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

public class GroupMainSwingTable implements ActionListener {
  public static JFrame mWin = new JFrame();
  final JTable groupsTbl;

  JPanel northPanel = new JPanel();
  JPanel centerPanel = new JPanel();
  JPanel southPanel = new JPanel();

  final JLabel errors = new JLabel();

  JLabel groupNameLabel = new JLabel("Group Name: ", JLabel.LEADING);
  JLabel specialtyLabel = new JLabel("Specialty: ", JLabel.LEADING);
  final JTextField groupName = new JTextField();
  final JComboBox<Specialty> specialty = new JComboBox<>();

  final JButton btnCreateGroup = new JButton("Створити групу");
  final JButton btnUpdateGroup = new JButton("Редагувати групу");
  final JButton btnDeleteGroup = new JButton("Видалити групу");

  final SpecialtyModel specialtyModel = new SpecialtyModel();
  final GroupModel groupModel = new GroupModel();


  GroupMainSwingTable() {
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
    mWin.setTitle("Групи");
    mWin.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    mWin.setLayout(new BorderLayout());

    northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.Y_AXIS));
    northPanel.add(groupNameLabel);
    northPanel.add(groupName);
    northPanel.add(specialtyLabel);
    northPanel.add(specialty);

    specialtyModel.getAllRecords().forEach(facultyOption ->
        specialty.addItem(facultyOption)
    );
    specialty.setSelectedIndex(-1);

    northPanel.add(specialty);
    northPanel.add(errors);
    mWin.add(northPanel, BorderLayout.NORTH);

    btnCreateGroup.addActionListener(this);
    btnUpdateGroup.addActionListener(this);
    btnDeleteGroup.addActionListener(this);

    centerPanel.add(btnCreateGroup);
    centerPanel.add(btnUpdateGroup);
    centerPanel.add(btnDeleteGroup);

    mWin.add(centerPanel, BorderLayout.CENTER);

    // Create table
    groupsTbl = new JTable() {
      @Override
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    };
    groupsTbl.setPreferredSize(new Dimension(600, 430));
    groupsTbl.addMouseListener(new MouseAdapter() {
      public void mousePressed(MouseEvent mouseEvent) {
        JTable table = (JTable) mouseEvent.getSource();
        Point point = mouseEvent.getPoint();
        int row = table.rowAtPoint(point);
        if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
          Object currentGroupName = table.getModel().getValueAt(row, 1);
          groupName.setText((String) currentGroupName);
          Object specialtyId = table.getModel().getValueAt(row, 2);
          specialty.setSelectedIndex(findSpecialityIndex(Integer.parseInt((String) specialtyId)));
        }
      }
    });

    // Push table in scroll panel
    JScrollPane sp = new JScrollPane();
    sp.setViewportView(groupsTbl);

    southPanel.setPreferredSize(new Dimension(600, 430));
    southPanel.add(sp);
    mWin.add(southPanel, BorderLayout.SOUTH);
    loadData();

    mWin.setVisible(true);
  }

  int findSpecialityIndex(int specialityId) {
    for (int i = 0; i < specialty.getItemCount(); i++) {
      if (specialty.getItemAt(i).getSpecId() == specialityId) {
        return i;
      }
    }
    return -1;
  }

  private void loadData() {
    DefaultTableModel groupsTableModel = new DefaultTableModel();
    // Add Table Columns
    for (String colName : groupModel.getColumnsNames()) {
      groupsTableModel.addColumn(colName);
    }
    ArrayList<Group> records = groupModel.getAllRecords();
    // Push data into the table
    for (Group group : records) {
      String id = group.getGroupId() + "";
      String specialtyId = group.getSpecId() + "";
      String[] Row = {id, group.getGroupName(), specialtyId};
      groupsTableModel.addRow(Row);
    }

    groupsTbl.setModel(groupsTableModel);
    groupsTbl.getColumnModel().getColumn(0).setPreferredWidth(20);
    groupsTbl.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
    groupsTbl.getColumnModel().getColumn(1).setPreferredWidth(40);
    groupsTbl.getColumnModel().getColumn(2).setPreferredWidth(20);
  }


  public static void main(String[] args) {
    new GroupMainSwingTable();
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    errors.setText("");
    if (e.getSource().equals(btnCreateGroup)) {
      groupModel.addGroup(groupName.getText(), ((Specialty) specialty.getModel().getSelectedItem()).getSpecId());
      groupName.setText("");
      specialty.setSelectedIndex(-1);
      loadData();
    } else if (e.getSource().equals(btnUpdateGroup)) {
      int selectedGroup = groupsTbl.getSelectedRow();
      Object groupId = groupsTbl.getModel().getValueAt(selectedGroup, 0);
      groupModel.updateGroup(Integer.parseInt((String) groupId), groupName.getText(), ((Specialty) specialty.getModel().getSelectedItem()).getSpecId());
      groupName.setText("");
      specialty.setSelectedIndex(-1);
      loadData();
    } else if (e.getSource().equals(btnDeleteGroup)) {
      int selectedGroup = groupsTbl.getSelectedRow();
      Object groupId = groupsTbl.getModel().getValueAt(selectedGroup, 0);
      boolean result = groupModel.deleteGroup(Integer.parseInt((String) groupId));
      if (result) {
        ((DefaultTableModel) groupsTbl.getModel()).removeRow(selectedGroup);
      } else {
        errors.setText("Something went wrong");
        errors.setForeground(Color.RED);
      }
    }
  }

}
