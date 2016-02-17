package org.cgiar.ccafs.ap.data.model;
// Generated Jan 28, 2016 8:58:13 AM by Hibernate Tools 4.3.1.Final


import java.util.Date;

/**
 * ProjectLeverage generated by hbm2java
 */
public class ProjectLeverage implements java.io.Serializable {


  /**
   * 
   */
  private static final long serialVersionUID = -4247450986711440166L;

  private Integer id;

  private int projectId;
  private String title;
  private Integer institution;


  private Integer year;
  private Integer flagship;
  private Double budget;
  private Institution myInstitution;


  private boolean isActive;


  private Date activeSince;

  private long createdBy;


  private long modifiedBy;


  private String modificationJustification;


  public ProjectLeverage() {
  }

  public ProjectLeverage(int projectId, String title, boolean isActive, Date activeSince, long createdBy,
    long modifiedBy, String modificationJustification) {
    this.projectId = projectId;
    this.title = title;
    this.isActive = isActive;
    this.activeSince = activeSince;
    this.createdBy = createdBy;
    this.modifiedBy = modifiedBy;
    this.modificationJustification = modificationJustification;
  }

  public ProjectLeverage(int projectId, String title, Integer institution, Integer year, Integer flagship,
    Double budget, boolean isActive, Date activeSince, long createdBy, long modifiedBy,
    String modificationJustification) {
    this.projectId = projectId;
    this.title = title;
    this.institution = institution;
    this.year = year;
    this.flagship = flagship;
    this.budget = budget;
    this.isActive = isActive;
    this.activeSince = activeSince;
    this.createdBy = createdBy;
    this.modifiedBy = modifiedBy;
    this.modificationJustification = modificationJustification;
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
    ProjectLeverage other = (ProjectLeverage) obj;
    if (id == null) {
      if (other.id != null) {
        return false;
      }
    } else if (!id.equals(other.id)) {
      return false;
    }
    return true;
  }

  public Date getActiveSince() {
    return this.activeSince;
  }

  public Double getBudget() {
    return this.budget;
  }

  public long getCreatedBy() {
    return this.createdBy;
  }


  public Integer getFlagship() {
    return this.flagship;
  }

  public Integer getId() {
    return this.id;
  }

  public Integer getInstitution() {
    return this.institution;
  }

  public String getModificationJustification() {
    return this.modificationJustification;
  }

  public long getModifiedBy() {
    return this.modifiedBy;
  }

  public Institution getMyInstitution() {
    return myInstitution;
  }

  public int getProjectId() {
    return this.projectId;
  }


  public String getTitle() {
    return this.title;
  }


  public Integer getYear() {
    return year;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  public boolean isIsActive() {
    return this.isActive;
  }

  public void setActiveSince(Date activeSince) {
    this.activeSince = activeSince;
  }

  public void setBudget(Double budget) {
    this.budget = budget;
  }

  public void setCreatedBy(long createdBy) {
    this.createdBy = createdBy;
  }

  public void setFlagship(Integer flagship) {
    this.flagship = flagship;
  }


  public void setId(Integer id) {
    this.id = id;
  }

  public void setInstitution(Integer institution) {
    this.institution = institution;
  }

  public void setIsActive(boolean isActive) {
    this.isActive = isActive;
  }

  public void setModificationJustification(String modificationJustification) {
    this.modificationJustification = modificationJustification;
  }

  public void setModifiedBy(long modifiedBy) {
    this.modifiedBy = modifiedBy;
  }

  public void setMyInstitution(Institution myInstitution) {
    this.myInstitution = myInstitution;
  }

  public void setProjectId(int projectId) {
    this.projectId = projectId;
  }

  public void setTitle(String title) {
    this.title = title;
  }


  public void setYear(Integer year) {
    this.year = year;
  }


}
