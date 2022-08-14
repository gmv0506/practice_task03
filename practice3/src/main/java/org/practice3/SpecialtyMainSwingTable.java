package org.practice3;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class SpecialtyMainSwingTable implements ActionListener {
  public static JFrame mWin = new JFrame();
  JPanel northPanel = new JPanel();
  JPanel centerPanel = new JPanel();
  JPanel southPanel = new JPanel();
  JLabel specialtyCodeLabel = new JLabel("Specialty Code: ", JLabel.LEADING);
  JLabel specialtyNameLabel = new JLabel("Specialty Name: ", JLabel.LEADING);
  JLabel facultyLabel = new JLabel("Faculty: ", JLabel.LEADING);
  final JTextField specialtyCode = new JTextField();
  final JTextField specialtyName = new JTextField();
  final JLabel errors = new JLabel();
  final JComboBox<Faculty> faculty = new JComboBox<>();
  final JButton btnCreateSpecialty = new JButton("Створити спеціальність");
  final JButton btnUpdateSpecialty = new JButton("Редагувати спеціальність");
  final JButton btnDeleteSpecialty = new JButton("Видалити спеціальність");
  final SpecialtyModel specialtyModel = new SpecialtyModel();
  final FacultyModel facultyModel = new FacultyModel();
  final JTable tblSpecialties;

  SpecialtyMainSwingTable() {
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
    mWin.setTitle("Спеціальності");
    mWin.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    mWin.setLayout(new BorderLayout());

    northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.Y_AXIS));
    northPanel.add(specialtyCodeLabel);
    northPanel.add(specialtyCode);
    northPanel.add(specialtyNameLabel);
    northPanel.add(specialtyName);
    northPanel.add(facultyLabel);

    facultyModel.getAllRecords().forEach(facultyOption ->
        faculty.addItem(facultyOption)
    );
    faculty.setSelectedIndex(-1);

    northPanel.add(faculty);
    northPanel.add(errors);
    mWin.add(northPanel, BorderLayout.NORTH);

    btnCreateSpecialty.addActionListener(this);
    btnUpdateSpecialty.addActionListener(this);
    btnDeleteSpecialty.addActionListener(this);

    centerPanel.add(btnCreateSpecialty);
    centerPanel.add(btnUpdateSpecialty);
    centerPanel.add(btnDeleteSpecialty);
    centerPanel.setMinimumSize(new Dimension(800, 150));
    mWin.add(centerPanel, BorderLayout.CENTER);

    // Create table
    tblSpecialties = new JTable() {
      @Override
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    };
    tblSpecialties.setPreferredSize(new Dimension(600, 400));
    tblSpecialties.addMouseListener(new MouseAdapter() {
      public void mousePressed(MouseEvent mouseEvent) {
        JTable table = (JTable) mouseEvent.getSource();
        Point point = mouseEvent.getPoint();
        int row = table.rowAtPoint(point);
        if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
          Object specCode = table.getModel().getValueAt(row, 1);
          Object specName = table.getModel().getValueAt(row, 2);
          Object facultyId = table.getModel().getValueAt(row, 3);
          specialtyCode.setText((String) specCode);
          specialtyName.setText((String) specName);
          faculty.setSelectedIndex(findSpecialityIndex(Integer.parseInt((String) facultyId)));
        }
      }
    });

    // Push table in scroll panel
    JScrollPane sp = new JScrollPane();
    sp.setViewportView(tblSpecialties);

    southPanel.setPreferredSize(new Dimension(600, 400));
    southPanel.add(sp);
    mWin.add(southPanel, BorderLayout.SOUTH);
    loadData();

    mWin.setVisible(true);
  }

  int findSpecialityIndex(int specialityId) {
    for (int i = 0; i < faculty.getItemCount(); i++) {
      if (faculty.getItemAt(i).getFacultyId() == specialityId) {
        return i;
      }
    }
    return -1;
  }

  private void loadData() {
    // Prepare data from facultyModel model
    DefaultTableModel specialtiesTableModel = new DefaultTableModel();
    // Add Table Columns
    for (String colName : specialtyModel.getColumnsNames()) {
      specialtiesTableModel.addColumn(colName);
    }
    ArrayList<Specialty> records = specialtyModel.getAllRecords();
    // Push data into the table
    for (Specialty specialty : records) {
      String id = specialty.getSpecId() + "";
      String facultyId = specialty.getFacultyId() + "";
      String[] Row = {id, specialty.getSpecCode(), specialty.getSpecName(), facultyId};
      specialtiesTableModel.addRow(Row);
    }

    tblSpecialties.setModel(specialtiesTableModel);
    tblSpecialties.getColumnModel().getColumn(0).setPreferredWidth(20);
    tblSpecialties.getColumnModel().getColumn(1).setPreferredWidth(40);
    tblSpecialties.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
    tblSpecialties.getColumnModel().getColumn(2).setPreferredWidth(80);
    tblSpecialties.getColumnModel().getColumn(3).setPreferredWidth(40);
  }


  public static void main(String[] args) {
    new SpecialtyMainSwingTable();
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    errors.setText("");
    if (e.getSource().equals(btnCreateSpecialty)) {
      specialtyModel.addSpecialty(specialtyCode.getText(), specialtyName.getText(), ((Faculty) faculty.getModel().getSelectedItem()).getFacultyId());
      specialtyCode.setText("");
      specialtyName.setText("");
      faculty.setSelectedIndex(-1);
      loadData();
    } else if (e.getSource().equals(btnUpdateSpecialty)) {
      int selectedSpecialty = tblSpecialties.getSelectedRow();
      Object specialtyId = tblSpecialties.getModel().getValueAt(selectedSpecialty, 0);
      specialtyModel.updateSpecialty(Integer.parseInt((String) specialtyId), specialtyCode.getText(), specialtyName.getText(), ((Faculty) faculty.getModel().getSelectedItem()).getFacultyId());
      loadData();
    } else if (e.getSource().equals(btnDeleteSpecialty)) {
      int selectedSpecialty = tblSpecialties.getSelectedRow();
      Object specialtyId = tblSpecialties.getModel().getValueAt(selectedSpecialty, 0);
      boolean result = specialtyModel.deleteSpecialty(Integer.parseInt((String) specialtyId));
      if (result) {
        ((DefaultTableModel) tblSpecialties.getModel()).removeRow(selectedSpecialty);
      } else {
        errors.setText("Something went wrong");
        errors.setForeground(Color.RED);
      }
    }
  }

}
