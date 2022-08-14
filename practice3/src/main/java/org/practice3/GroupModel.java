package org.practice3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class GroupModel {
  private final static String USER = "root24";
  private final static String PASS = "root";
  private final static String URL = "jdbc:mysql://localhost:3306/inf_sys_practice";
  private final String[] COLUMNS = {"group_id", "group_name", "spec_id"};

  public ArrayList<Group> getAllRecords() {
    ResultSet result;
    ArrayList<Group> faculties = new ArrayList<>();
    try (Connection connection = DriverManager.getConnection(URL, USER, PASS);
         Statement statement = connection.createStatement()) {
      String TABLE = "s_groups";
      result = statement.executeQuery("SELECT * FROM " + TABLE + " ORDER BY spec_id ASC");
      while (result.next()) {
        Group record = new Group(result.getInt("group_id"), result.getString("group_name"), result.getInt("spec_id"));
        faculties.add(record);
      }
      result.close();
    } catch (SQLException e) {
      System.out.println(e);
    }
    return faculties;
  }

  public void addGroup(String groupName, int specialityId) {
    String SQL = "INSERT into s_groups (group_name, spec_id) values (?,?)";
    try (Connection connection = DriverManager.getConnection(URL, USER, PASS);
         PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
      preparedStatement.setString(1, groupName);
      preparedStatement.setInt(2, specialityId);
      preparedStatement.execute();
    } catch (SQLException e) {
      System.out.println(e);
    }
  }

  public void updateGroup(int groupId, String groupName, int specialityId) {
    String SQL = "UPDATE s_groups SET group_name=?,spec_id=? WHERE group_id = ?";
    try (Connection connection = DriverManager.getConnection(URL, USER, PASS);
         PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
      preparedStatement.setString(1, groupName);
      preparedStatement.setInt(2, specialityId);
      preparedStatement.setInt(3, groupId);
      preparedStatement.execute();
    } catch (SQLException e) {
      System.out.println(e);
    }
  }

  public boolean deleteGroup(int groupId) {
    String SQL = "DELETE FROM s_groups WHERE group_id = ?";
    try (Connection connection = DriverManager.getConnection(URL, USER, PASS);
         PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
      preparedStatement.setInt(1, groupId);
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

