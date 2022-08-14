package org.practice3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class StudentModel {
  private final static String USER = "root24";
  private final static String PASS = "root";
  private final static String URL = "jdbc:mysql://localhost:3306/inf_sys_practice";
  private final String[] COLUMNS = {"student_id", "surname", "name", "fname", "group_id"};

  public ArrayList<Student> getAllRecords() {
    ResultSet result;
    ArrayList<Student> students = new ArrayList<>();
    try (Connection connection = DriverManager.getConnection(URL, USER, PASS);
         Statement statement = connection.createStatement()) {
      String TABLE = "students";
      result = statement.executeQuery("SELECT * FROM " + TABLE + " ORDER BY student_id ASC");
      while (result.next()) {
        Student record = new Student(result.getInt("student_id"), result.getString("surname"), result.getString("name"), result.getString("fname"), result.getInt("group_id"));
        students.add(record);
      }
      result.close();
    } catch (SQLException e) {
      System.out.println(e);
    }
    return students;
  }

  public void addStudent(String surname, String name, String fname, int groupId) {
    String SQL = "INSERT into students (surname,name,fname,group_id) values (?,?,?,?)";
    try (Connection connection = DriverManager.getConnection(URL, USER, PASS);
         PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
      preparedStatement.setString(1, surname);
      preparedStatement.setString(2, name);
      preparedStatement.setString(3, fname);
      preparedStatement.setInt(4, groupId);
      preparedStatement.execute();
    } catch (SQLException e) {
      System.out.println(e);
    }
  }

  public void updateStudent(int studentId, String surname, String name, String fname, int groupId) {
    String SQL = "UPDATE students SET surname=?,name=?,fname=?,group_id=? WHERE student_id = ?";
    try (Connection connection = DriverManager.getConnection(URL, USER, PASS);
         PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
      preparedStatement.setString(1, surname);
      preparedStatement.setString(2, name);
      preparedStatement.setString(3, fname);
      preparedStatement.setInt(4, groupId);
      preparedStatement.setInt(5, studentId);
      preparedStatement.execute();
    } catch (SQLException e) {
      System.out.println(e);
    }
  }

  public boolean deleteStudent(int studentId) {
    String SQL = "DELETE FROM students WHERE student_id = ?";
    try (Connection connection = DriverManager.getConnection(URL, USER, PASS);
         PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
      preparedStatement.setInt(1, studentId);
      preparedStatement.execute();
      return true;
    } catch (SQLException e) {
      System.out.println(e);
      return false;
    }
  }

  public String[] getColumnsNames() {
    return this.COLUMNS;
  }
}

