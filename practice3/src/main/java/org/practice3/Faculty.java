package org.practice3;

public class Faculty {
  private int facultyId;
  private String facultyName;

  public Faculty(int facultyId, String facultyName) {
    this.facultyId = facultyId;
    this.facultyName = facultyName;
  }


  public int getFacultyId() {
    return facultyId;
  }

  public void setFacultyId(int facultyId) {
    this.facultyId = facultyId;
  }

  public String getFacultyName() {
    return facultyName;
  }

  public void setFacultyName(String facultyName) {
    this.facultyName = facultyName;
  }

  @Override
  public String toString() {
    return facultyId + ": " + facultyName;
  }
}
