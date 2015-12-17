package org.cgiar.ccafs.ap.data.model;
// Generated Dec 17, 2015 11:07:06 AM by Hibernate Tools 3.5.0.Final


import java.util.Date;

/**
 * OtherContributions generated by hbm2java
 */
public class OtherContributions implements java.io.Serializable {


  private Integer id;
  private int projectId;
  private String region;
  private String flagship;
  private String indicators;
  private String description;
  private Integer target;
  private boolean isActive;
  private Date activeSince;
  private long createdBy;
  private long modifiedBy;
  private String modificationJustification;

  public OtherContributions() {
  }


  public OtherContributions(int projectId, boolean isActive, Date activeSince, long createdBy, long modifiedBy,
    String modificationJustification) {
    this.projectId = projectId;
    this.isActive = isActive;
    this.activeSince = activeSince;
    this.createdBy = createdBy;
    this.modifiedBy = modifiedBy;
    this.modificationJustification = modificationJustification;
  }

  public OtherContributions(int projectId, String region, String flagship, String indicators, String description,
    Integer target, boolean isActive, Date activeSince, long createdBy, long modifiedBy,
    String modificationJustification) {
    this.projectId = projectId;
    this.region = region;
    this.flagship = flagship;
    this.indicators = indicators;
    this.description = description;
    this.target = target;
    this.isActive = isActive;
    this.activeSince = activeSince;
    this.createdBy = createdBy;
    this.modifiedBy = modifiedBy;
    this.modificationJustification = modificationJustification;
  }

  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public int getProjectId() {
    return this.projectId;
  }

  public void setProjectId(int projectId) {
    this.projectId = projectId;
  }

  public String getRegion() {
    return this.region;
  }

  public void setRegion(String region) {
    this.region = region;
  }

  public String getFlagship() {
    return this.flagship;
  }

  public void setFlagship(String flagship) {
    this.flagship = flagship;
  }

  public String getIndicators() {
    return this.indicators;
  }

  public void setIndicators(String indicators) {
    this.indicators = indicators;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Integer getTarget() {
    return this.target;
  }

  public void setTarget(Integer target) {
    this.target = target;
  }

  public boolean isIsActive() {
    return this.isActive;
  }

  public void setIsActive(boolean isActive) {
    this.isActive = isActive;
  }

  public Date getActiveSince() {
    return this.activeSince;
  }

  public void setActiveSince(Date activeSince) {
    this.activeSince = activeSince;
  }

  public long getCreatedBy() {
    return this.createdBy;
  }

  public void setCreatedBy(long createdBy) {
    this.createdBy = createdBy;
  }

  public long getModifiedBy() {
    return this.modifiedBy;
  }

  public void setModifiedBy(long modifiedBy) {
    this.modifiedBy = modifiedBy;
  }

  public String getModificationJustification() {
    return this.modificationJustification;
  }

  public void setModificationJustification(String modificationJustification) {
    this.modificationJustification = modificationJustification;
  }


}

