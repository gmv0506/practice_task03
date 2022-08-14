package org.practice3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SpecialtyModel {
  private final static String USER = "root24";
  private final static String PASS = "root";
  private final static String URL = "jdbc:mysql://localhost:3306/inf_sys_practice";
  private final String[] COLUMNS = {"spec_id", "spec_code", "spec_name", "faculty_id"};

  public ArrayList<Specialty> getAllRecords() {
    ResultSet result;
    ArrayList<Specialty> faculties = new ArrayList<>();
    try (Connection connection = DriverManager.getConnection(URL, USER, PASS);
         Statement statement = connection.createStatement()) {
      String TABLE = "specialty";
      result = statement.executeQuery("SELECT * FROM " + TABLE + " ORDER BY spec_id ASC");
      while (result.next()) {
        Specialty record = new Specialty(result.getInt("spec_id"), result.getString("spec_code"), result.getString("spec_name"), result.getInt("faculty_id"));
        faculties.add(record);
      }
      result.close();
    } catch (SQLException e) {
      System.out.println(e);
    }
    return faculties;
  }

  public void addSpecialty(String specialtyCode, String specialtyName, int facultyId) {
    String SQL = "INSERT into specialty (spec_code,spec_name,faculty_id) values (?,?,?)";
    try (Connection connection = DriverManager.getConnection(URL, USER, PASS);
         PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
      preparedStatement.setString(1, specialtyCode);
      preparedStatement.setString(2, specialtyName);
      preparedStatement.setInt(3, facultyId);
      preparedStatement.execute();
    } catch (SQLException e) {
      System.out.println(e);
    }
  }

  public void updateSpecialty(int specialityId, String specialtyCode, String specialtyName, int facultyId) {
    String SQL = "UPDATE specialty SET spec_code=?,spec_name=?,faculty_id=? WHERE spec_id = ?";
    try (Connection connection = DriverManager.getConnection(URL, USER, PASS);
         PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
      preparedStatement.setString(1, specialtyCode);
      preparedStatement.setString(2, specialtyName);
      preparedStatement.setInt(3, facultyId);
      preparedStatement.setInt(4, specialityId);
      preparedStatement.execute();
    } catch (SQLException e) {
      System.out.println(e);
    }
  }

  public boolean deleteSpecialty(int specialtyId) {
    String SQL = "DELETE FROM specialty WHERE spec_id = ?";
    try (Connection connection = DriverManager.getConnection(URL, USER, PASS);
         PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
      preparedStatement.setInt(1, specialtyId);
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

