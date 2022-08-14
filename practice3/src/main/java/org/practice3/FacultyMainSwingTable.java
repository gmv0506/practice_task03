package org.practice3;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class FacultyMainSwingTable implements ActionListener, TableModelListener {
  public static JFrame mWin = new JFrame();
  JPanel northPanel = new JPanel();
  JPanel centerPanel = new JPanel();
  JPanel southPanel = new JPanel();
  JLabel facultyNameLabel = new JLabel("Faculty Name: ", JLabel.LEADING);
  JLabel errors = new JLabel();
  final JTextField facultyId = new JTextField();
  final JTextField facultyName = new JTextField();
  final JButton btnCreateFaculty = new JButton("Створити факультет");
  final JButton btnDeleteFaculty = new JButton("Видалити факультет");
  final FacultyModel facultyModel = new FacultyModel();
  final JTable tblFaculties;

  FacultyMainSwingTable() {
    final int WIDTH = 800;
    final int HEIGHT = 600;
// Get information about screen
    Toolkit kit = Toolkit.getDefaultToolkit();
    Dimension screenSize = kit.getScreenSize();
    int screenHeight = screenSize.height;
    int screenWidth = screenSize.width;
// set frame attributes
    mWin.setSize(WIDTH, HEIGHT);
    mWin.setLocation((screenWidth - WIDTH) / 2, (screenHeight - HEIGHT) / 2);
    mWin.setResizable(false);
    mWin.setTitle("Факультети");
    mWin.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    mWin.setLayout(new BorderLayout());

    btnCreateFaculty.addActionListener(this);
    btnDeleteFaculty.addActionListener(this);

    northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.Y_AXIS));
    facultyId.setEditable(false);
    northPanel.add(facultyNameLabel);
    northPanel.add(facultyName);
    northPanel.add(errors);
    mWin.add(northPanel, BorderLayout.NORTH);
    centerPanel.add(btnCreateFaculty);
    centerPanel.add(btnDeleteFaculty);
    mWin.add(centerPanel, BorderLayout.CENTER);

    // Create table
    tblFaculties = new JTable() {
      @Override
      public boolean isCellEditable(int row, int column) {
        return column != 0;
      }
    };
    // Push table in scroll panel
    JScrollPane sp = new JScrollPane();
    sp.setViewportView(tblFaculties);
    southPanel.add(sp);
    mWin.add(southPanel, BorderLayout.SOUTH);
    loadData();

    mWin.setVisible(true);
  }

  private void loadData() {
    // Prepare data from facultyModel model
    DefaultTableModel facultiesTableModel = new DefaultTableModel();
    // Add Table Columns
    for (String colName : facultyModel.getColumnsNames()) {
      facultiesTableModel.addColumn(colName);
    }
    ArrayList<Faculty> records = facultyModel.getAllRecords();
    // Push data into the table
    for (Faculty faculty : records) {
      String id = faculty.getFacultyId() + "";
      String[] Row = {id, faculty.getFacultyName()};
      facultiesTableModel.addRow(Row);
    }
    facultiesTableModel.addTableModelListener(this);
    tblFaculties.setModel(facultiesTableModel);
    tblFaculties.getColumnModel().getColumn(0).setPreferredWidth(20);
    tblFaculties.getColumnModel().getColumn(1).setPreferredWidth(200);
    tblFaculties.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
  }


  public static void main(String[] args) {
    new FacultyMainSwingTable();
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    errors.setText("");
    if (e.getSource().equals(btnCreateFaculty)) {
      facultyModel.addFaculty(facultyName.getText());
      facultyName.setText("");
      loadData();
    } else if (e.getSource().equals(btnDeleteFaculty)) {
      int selectedFaculty = tblFaculties.getSelectedRow();
      Object facultyId = tblFaculties.getModel().getValueAt(selectedFaculty, 0);
      boolean result = facultyModel.deleteFaculty(Integer.parseInt((String) facultyId));
      if (result) {
        ((DefaultTableModel) tblFaculties.getModel()).removeRow(selectedFaculty);
      } else {
        errors.setText("Something went wrong");
        errors.setForeground(Color.RED);
      }
    }
  }

  @Override
  public void tableChanged(TableModelEvent e) {
    errors.setText("");
    if (e.getType() != TableModelEvent.DELETE) {
      int row = e.getFirstRow();
      int column = e.getColumn();
      TableModel model = (TableModel) e.getSource();
      Object data = model.getValueAt(row, column);
      Object facultyId = model.getValueAt(row, 0);
      facultyModel.renameFaculty((String) data, Integer.parseInt((String) facultyId));
    }
  }
}
