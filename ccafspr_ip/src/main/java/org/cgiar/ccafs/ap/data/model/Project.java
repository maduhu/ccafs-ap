package org.cgiar.ccafs.ap.data.model;

import org.apache.commons.lang3.builder.ToStringBuilder;


public class Project {

  public int id;
  public String title;

  public Project() {

  }

  public Project(int id, String title) {
    this.id = id;
    this.title = title;
  }

  public int getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }


  public void setId(int id) {
    this.id = id;
  }


  public void setTitle(String title) {
    this.title = title;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}