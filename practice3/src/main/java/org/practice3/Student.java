package org.practice3;

public class Student {
  private int id;
  private String surname;
  private String name;
  private String fName;
  private int groupId;

  public Student(int id, String surname, String name, String fName, int groupId) {
    this.id = id;
    this.surname = surname;
    this.name = name;
    this.fName = fName;
    this.groupId = groupId;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getSurname() {
    return surname;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getfName() {
    return fName;
  }

  public void setfName(String fName) {
    this.fName = fName;
  }

  public int getGroupId() {
    return groupId;
  }

  public void setGroupId(int groupId) {
    this.groupId = groupId;
  }
}