package org.cgiar.ccafs.ap.data.model;
// Generated Jan 26, 2016 9:41:11 AM by Hibernate Tools 4.3.1.Final


import java.util.Date;

/**
 * ProjectNextUser generated by hbm2java
 */
public class ProjectNextUser implements java.io.Serializable {


  /**
   * 
   */
  private static final long serialVersionUID = -285191648622361767L;

  private int id;

  private int projectId;
  private String strategies;
  private String keyNextUser;
  private String reportedDeliverables;
  private String lessonsImplications;

  private boolean isActive;


  private Date activeSince;

  private long createdBy;
  private long modifiedBy;
  private String modificationJustification;

  public ProjectNextUser() {
  }

  public ProjectNextUser(int id, int projectId, boolean isActive, Date activeSince, long createdBy, long modifiedBy,
    String modificationJustification) {
    this.id = id;
    this.projectId = projectId;
    this.isActive = isActive;
    this.activeSince = activeSince;
    this.createdBy = createdBy;
    this.modifiedBy = modifiedBy;
    this.modificationJustification = modificationJustification;
  }

  public ProjectNextUser(int id, int projectId, String strategies, String reportedDeliverables,
    String lessonsImplications, boolean isActive, Date activeSince, long createdBy, long modifiedBy,
    String modificationJustification) {
    this.id = id;
    this.projectId = projectId;
    this.strategies = strategies;
    this.reportedDeliverables = reportedDeliverables;
    this.lessonsImplications = lessonsImplications;
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
    ProjectNextUser other = (ProjectNextUser) obj;
    if (id != other.id) {
      return false;
    }
    return true;
  }

  public Date getActiveSince() {
    return this.activeSince;
  }


  public long getCreatedBy() {
    return this.createdBy;
  }

  public int getId() {
    return this.id;
  }

  public String getKeyNextUser() {
    return keyNextUser;
  }

  public String getLessonsImplications() {
    return this.lessonsImplications;
  }

  public String getModificationJustification() {
    return this.modificationJustification;
  }

  public long getModifiedBy() {
    return this.modifiedBy;
  }

  public int getProjectId() {
    return this.projectId;
  }

  public String getReportedDeliverables() {
    return this.reportedDeliverables;
  }

  public String getStrategies() {
    return this.strategies;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + id;
    return result;
  }

  public boolean isIsActive() {
    return this.isActive;
  }

  public void setActiveSince(Date activeSince) {
    this.activeSince = activeSince;
  }

  public void setCreatedBy(long createdBy) {
    this.createdBy = createdBy;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setIsActive(boolean isActive) {
    this.isActive = isActive;
  }

  public void setKeyNextUser(String keyNextUser) {
    this.keyNextUser = keyNextUser;
  }

  public void setLessonsImplications(String lessonsImplications) {
    this.lessonsImplications = lessonsImplications;
  }

  public void setModificationJustification(String modificationJustification) {
    this.modificationJustification = modificationJustification;
  }

  public void setModifiedBy(long modifiedBy) {
    this.modifiedBy = modifiedBy;
  }

  public void setProjectId(int projectId) {
    this.projectId = projectId;
  }

  public void setReportedDeliverables(String reportedDeliverables) {
    this.reportedDeliverables = reportedDeliverables;
  }

  public void setStrategies(String strategies) {
    this.strategies = strategies;
  }


}
