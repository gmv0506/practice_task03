package org.practice3;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AppGui implements ActionListener {
  JFrame eF = new JFrame("Робота з базою даних");
  JPanel centralPanel = new JPanel();
  JPanel southPanel = new JPanel();
  JLabel lbEr = new JLabel();

  JButton btnFaculties = new JButton("Факультети");
  JButton btnSpecialities = new JButton("Спеціальності");
  JButton btnGroups = new JButton("Групи");
  JButton btnStudents = new JButton("Студенти");


  public AppGui() {
    eF.setSize(500, 200);
    eF.setResizable(false);
    eF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    eF.setLayout(new BorderLayout());

    centralPanel.add(btnFaculties);
    centralPanel.add(btnSpecialities);
    centralPanel.add(btnGroups);
    centralPanel.add(btnStudents);

    southPanel.add(lbEr);
    eF.add(centralPanel, BorderLayout.CENTER);
    eF.add(southPanel, BorderLayout.SOUTH);
    this.btnFaculties.addActionListener(this);
    this.btnSpecialities.addActionListener(this);
    this.btnGroups.addActionListener(this);
    this.btnStudents.addActionListener(this);
    eF.setVisible(true);
  }

  public static void main(String[] args) {
    new AppGui();
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource().equals(btnFaculties)) {
      new FacultyMainSwingTable();
    } else if (e.getSource().equals(btnSpecialities)) {
      new SpecialtyMainSwingTable();
    } else if (e.getSource().equals(btnGroups)) {
      new GroupMainSwingTable();
    } else if (e.getSource().equals(btnStudents)) {
      new StudentMainSwingTable();
    }
  }
}
