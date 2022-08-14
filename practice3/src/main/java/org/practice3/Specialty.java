package org.practice3;

public class Specialty {
  private int specId;
  private String specCode;
  private String specName;
  private int facultyId;

  public Specialty(int specId, String specCode, String specName, int facultyId) {
    this.specId = specId;
    this.specCode = specCode;
    this.specName = specName;
    this.facultyId = facultyId;
  }

  public int getSpecId() {
    return specId;
  }

  public void setSpecId(int specId) {
    this.specId = specId;
  }

  public String getSpecCode() {
    return specCode;
  }

  public void setSpecCode(String specCode) {
    this.specCode = specCode;
  }

  public String getSpecName() {
    return specName;
  }

  public void setSpecName(String specName) {
    this.specName = specName;
  }

  public int getFacultyId() {
    return facultyId;
  }

  public void setFacultyId(int facultyId) {
    this.facultyId = facultyId;
  }

  @Override
  public String toString() {
    return getSpecName();
  }
}
