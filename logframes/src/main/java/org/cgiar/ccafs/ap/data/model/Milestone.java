package org.cgiar.ccafs.ap.data.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author hftobon
 */
public class Milestone {

  private int id;
  private String code;
  private int year;
  private String description;
  private Output output;

  public Milestone() {
  }

  public Milestone(int id) {
    this.id = id;
  }

  public String getCode() {
    return code;
  }

  public String getDescription() {
    return description;
  }

  public int getId() {
    return id;
  }

  public Output getOutput() {
    return output;
  }

  public int getYear() {
    return year;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setOutput(Output output) {
    this.output = output;
  }

  public void setYear(int year) {
    this.year = year;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

}
