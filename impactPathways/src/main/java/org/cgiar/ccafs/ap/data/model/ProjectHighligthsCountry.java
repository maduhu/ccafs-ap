package org.cgiar.ccafs.ap.data.model;
// Generated Dec 7, 2015 8:15:11 AM by Hibernate Tools 3.5.0.Final


/**
 * ProjectHighligthsCountry generated by hbm2java
 */
public class ProjectHighligthsCountry implements java.io.Serializable {


  /**
   * 
   */
  private static final long serialVersionUID = -3031750914438220963L;
  private Integer id;
  private ProjectHighligths projectHighligths;
  private int idCountry;
  private Country country;

  public ProjectHighligthsCountry() {
  }

  public ProjectHighligthsCountry(ProjectHighligths projectHighligths, int idCountry) {
    this.projectHighligths = projectHighligths;
    this.idCountry = idCountry;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (this.getClass() != obj.getClass()) {
      return false;
    }
    ProjectHighligthsCountry other = (ProjectHighligthsCountry) obj;
    if (idCountry != other.idCountry) {
      return false;
    }
    return true;
  }

  public Country getCountry() {
    return country;
  }

  public Integer getId() {
    return this.id;
  }

  public int getIdCountry() {
    return this.idCountry;
  }

  public ProjectHighligths getProjectHighligths() {
    return this.projectHighligths;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + idCountry;
    return result;
  }

  public void setCountry(Country country) {
    this.country = country;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public void setIdCountry(int idCountry) {
    this.idCountry = idCountry;
  }

  public void setProjectHighligths(ProjectHighligths projectHighligths) {
    this.projectHighligths = projectHighligths;
  }


}

