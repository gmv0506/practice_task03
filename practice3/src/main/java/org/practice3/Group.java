package org.practice3;

public class Group {
  private int groupId;
  private String groupName;
  private int specId;

  public Group(int groupId, String groupName, int specId) {
    this.groupId = groupId;
    this.groupName = groupName;
    this.specId = specId;
  }

  public int getGroupId() {
    return groupId;
  }

  public void setGroupId(int groupId) {
    this.groupId = groupId;
  }

  public String getGroupName() {
    return groupName;
  }

  public void setGroupName(String groupName) {
    this.groupName = groupName;
  }

  public int getSpecId() {
    return specId;
  }

  public void setSpecId(int specId) {
    this.specId = specId;
  }

  @Override
  public String toString() {
    return groupName;
  }
}
