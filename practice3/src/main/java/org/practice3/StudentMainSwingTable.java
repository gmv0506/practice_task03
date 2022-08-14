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
import javax.swing.table.DefaultTableModel;
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

public class StudentMainSwingTable implements ActionListener {
  public static JFrame mWin = new JFrame();
  JPanel northPanel = new JPanel();
  JPanel centerPanel = new JPanel();
  JPanel southPanel = new JPanel();
  JLabel surnameLabel = new JLabel("Surname: ", JLabel.LEADING);
  JLabel nameLabel = new JLabel("Name: ", JLabel.LEADING);
  JLabel fatherNameLabel = new JLabel("Father's Name: ", JLabel.LEADING);
  JLabel groupLabel = new JLabel("Group: ", JLabel.LEADING);

  final JTextField surname = new JTextField();
  final JTextField name = new JTextField();
  final JTextField fatherName = new JTextField();

  final JLabel errors = new JLabel();
  final JComboBox<Group> group = new JComboBox<>();

  final JButton createStudentBtn = new JButton("Створити студента");
  final JButton updateStudentBtn = new JButton("Редагувати студента");
  final JButton deleteStudentBtn = new JButton("Видалити студента");
  final GroupModel groupModel = new GroupModel();
  final StudentModel studentModel = new StudentModel();
  final JTable studentsTbl;

  StudentMainSwingTable() {
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
    mWin.setTitle("Студенти");
    mWin.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    mWin.setLayout(new BorderLayout());

    northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.Y_AXIS));
    northPanel.add(surnameLabel);
    northPanel.add(surname);
    northPanel.add(nameLabel);
    northPanel.add(name);
    northPanel.add(fatherNameLabel);
    northPanel.add(fatherName);
    northPanel.add(groupLabel);
    northPanel.add(group);

    groupModel.getAllRecords().forEach(group::addItem);
    group.setSelectedIndex(-1);

    northPanel.add(group);
    northPanel.add(errors);
    mWin.add(northPanel, BorderLayout.NORTH);

    createStudentBtn.addActionListener(this);
    updateStudentBtn.addActionListener(this);
    deleteStudentBtn.addActionListener(this);

    centerPanel.add(createStudentBtn);
    centerPanel.add(updateStudentBtn);
    centerPanel.add(deleteStudentBtn);
    centerPanel.setMinimumSize(new Dimension(800, 150));
    mWin.add(centerPanel, BorderLayout.CENTER);

    // Create table
    studentsTbl = new JTable() {
      @Override
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    };
    studentsTbl.setPreferredSize(new Dimension(600, 350));
    studentsTbl.addMouseListener(new MouseAdapter() {
      public void mousePressed(MouseEvent mouseEvent) {
        JTable table = (JTable) mouseEvent.getSource();
        Point point = mouseEvent.getPoint();
        int row = table.rowAtPoint(point);
        if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
          Object currentSurname = table.getModel().getValueAt(row, 1);
          Object currentName = table.getModel().getValueAt(row, 2);
          Object currentFname = table.getModel().getValueAt(row, 3);
          Object groupId = table.getModel().getValueAt(row, 4);
          surname.setText((String) currentSurname);
          name.setText((String) currentName);
          fatherName.setText((String) currentFname);
          group.setSelectedIndex(findGroupIndex(Integer.parseInt((String) groupId)));
        }
      }
    });

    // Push table in scroll panel
    JScrollPane sp = new JScrollPane();
    sp.setViewportView(studentsTbl);

    southPanel.setPreferredSize(new Dimension(600, 350));
    southPanel.add(sp);
    mWin.add(southPanel, BorderLayout.SOUTH);
    loadData();

    mWin.setVisible(true);
  }

  int findGroupIndex(int groupId) {
    for (int i = 0; i < group.getItemCount(); i++) {
      if (group.getItemAt(i).getGroupId() == groupId) {
        return i;
      }
    }
    return -1;
  }

  private void loadData() {
    DefaultTableModel studentsTblModel = new DefaultTableModel();
    // Add Table Columns
    for (String colName : studentModel.getColumnsNames()) {
      studentsTblModel.addColumn(colName);
    }
    ArrayList<Student> records = studentModel.getAllRecords();
    // Push data into the table
    for (Student student : records) {
      String id = student.getId() + "";
      String surname = student.getSurname();
      String name = student.getName();
      String fname = student.getfName();
      String groupId = student.getGroupId() + "";
      String[] Row = {id, surname, name, fname, groupId};
      studentsTblModel.addRow(Row);
    }

    studentsTbl.setModel(studentsTblModel);
    studentsTbl.getColumnModel().getColumn(0).setPreferredWidth(20);
    studentsTbl.getColumnModel().getColumn(1).setPreferredWidth(40);
    studentsTbl.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
    studentsTbl.getColumnModel().getColumn(2).setPreferredWidth(80);
    studentsTbl.getColumnModel().getColumn(3).setPreferredWidth(40);
  }


  public static void main(String[] args) {
    new StudentMainSwingTable();
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    errors.setText("");
    if (e.getSource().equals(createStudentBtn)) {
      studentModel.addStudent(surname.getText(), name.getText(), fatherName.getText(), ((Group) group.getModel().getSelectedItem()).getGroupId());
      surname.setText("");
      name.setText("");
      fatherName.setText("");
      group.setSelectedIndex(-1);
      loadData();
    } else if (e.getSource().equals(updateStudentBtn)) {
      int selectedSpecialty = studentsTbl.getSelectedRow();
      Object studentId = studentsTbl.getModel().getValueAt(selectedSpecialty, 0);
      studentModel.updateStudent(Integer.parseInt((String) studentId), surname.getText(), name.getText(), fatherName.getText(), ((Group) group.getModel().getSelectedItem()).getGroupId());
      surname.setText("");
      name.setText("");
      fatherName.setText("");
      group.setSelectedIndex(-1);
      loadData();
    } else if (e.getSource().equals(deleteStudentBtn)) {
      int selectedStudent = studentsTbl.getSelectedRow();
      Object studentId = studentsTbl.getModel().getValueAt(selectedStudent, 0);
      boolean result = studentModel.deleteStudent(Integer.parseInt((String) studentId));
      if (result) {
        ((DefaultTableModel) studentsTbl.getModel()).removeRow(selectedStudent);
      } else {
        errors.setText("Something went wrong");
        errors.setForeground(Color.RED);
      }
    }
  }

}
