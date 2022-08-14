package org.practice3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class FacultyModel {
  private final static String USER = "root24";
  private final static String PASS = "root";
  private final static String URL = "jdbc:mysql://localhost:3306/inf_sys_practice";
  private final String[] COLUMNS = {"faculty_id", "faculty_name"};

  public ArrayList<Faculty> getAllRecords() {
    ResultSet result;
    ArrayList<Faculty> faculties = new ArrayList<>();
    try (Connection connection = DriverManager.getConnection(URL, USER, PASS);
         Statement statement = connection.createStatement()) {
      String TABLE = "faculty";
      result = statement.executeQuery("SELECT * FROM " + TABLE + " ORDER BY faculty_id ASC");
      while (result.next()) {
        Faculty record = new Faculty(result.getInt("faculty_id"), result.getString("faculty_name"));
        faculties.add(record);
      }
      result.close();
    } catch (SQLException e) {
      System.out.println(e);
    }
    return faculties;
  }

  public void addFaculty(String facultyName) {
    String SQL = "INSERT into faculty (faculty_name) values (?)";
    try (Connection connection = DriverManager.getConnection(URL, USER, PASS);
         PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
      preparedStatement.setString(1, facultyName);
      preparedStatement.execute();
    } catch (SQLException e) {
      System.out.println(e);
    }
  }

  public void renameFaculty(String newFacultyName, int facultyId) {
    String SQL = "UPDATE faculty SET faculty_name = ? WHERE faculty_id = ?";
    try (Connection connection = DriverManager.getConnection(URL, USER, PASS);
         PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
      preparedStatement.setString(1, newFacultyName);
      preparedStatement.setInt(2, facultyId);
      preparedStatement.execute();
    } catch (SQLException e) {
      System.out.println(e);
    }
  }

  public boolean deleteFaculty(int facultyId) {
    String SQL = "DELETE FROM faculty WHERE faculty_id = ?";
    try (Connection connection = DriverManager.getConnection(URL, USER, PASS);
         PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
      preparedStatement.setInt(1, facultyId);
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
