package org.cgiar.ccafs.ap.data.model;
// Generated Jan 14, 2016 3:04:11 PM by Hibernate Tools 4.3.1.Final


import java.util.Date;

/**
 * DeliverableDissemination generated by hbm2java
 */
public class DeliverableDissemination implements java.io.Serializable {


  private Integer id;
  private int deliverableId;
  private String type;

  private Boolean isOpenAccess;


  private Boolean intellectualProperty;

  private Boolean limitedExclusivity;
  private Boolean restrictedUseAgreement;
  private Date restrictedAccessUntil;
  private Boolean effectiveDateRestriction;
  private Date restrictedEmbargoed;
  private Boolean alreadyDisseminated;
  private String disseminationChannel;
  private String disseminationUrl;
  private String disseminationChannelName;
  private String descriptionMetadata;
  private String authorsMetadata;
  private String identifierMetadata;
  private String publishierMetadata;
  private String relationMetadata;
  private String contributorMetadata;
  private String subjectMetadata;
  private String sourceMetadata;
  private String publicationMetada;
  private String languageMetadata;
  private String coverageMetadata;
  private String formatMetadata;
  private String rigthsMetadata;

  public DeliverableDissemination() {
  }

  public DeliverableDissemination(int deliverableId) {
    this.deliverableId = deliverableId;
  }

  public DeliverableDissemination(int deliverableId, Boolean isOpenAccess, Boolean intellectualProperty,
    Boolean limitedExclusivity, Boolean restrictedUseAgreement, Date restrictedAccessUntil,
    Boolean effectiveDateRestriction, Date restrictedEmbargoed, Boolean alreadyDisseminated,
    String disseminationChannel, String disseminationUrl, String disseminationChannelName, String descriptionMetadata,
    String authorsMetadata, String identifierMetadata, String publishierMetadata, String relationMetadata,
    String contributorMetadata, String subjectMetadata, String sourceMetadata, String publicationMetada,
    String languageMetadata, String coverageMetadata, String formatMetadata, String rigthsMetadata) {
    this.deliverableId = deliverableId;
    this.isOpenAccess = isOpenAccess;
    this.intellectualProperty = intellectualProperty;
    this.limitedExclusivity = limitedExclusivity;
    this.restrictedUseAgreement = restrictedUseAgreement;
    this.restrictedAccessUntil = restrictedAccessUntil;
    this.effectiveDateRestriction = effectiveDateRestriction;
    this.restrictedEmbargoed = restrictedEmbargoed;
    this.alreadyDisseminated = alreadyDisseminated;
    this.disseminationChannel = disseminationChannel;
    this.disseminationUrl = disseminationUrl;
    this.disseminationChannelName = disseminationChannelName;
    this.descriptionMetadata = descriptionMetadata;
    this.authorsMetadata = authorsMetadata;
    this.identifierMetadata = identifierMetadata;
    this.publishierMetadata = publishierMetadata;
    this.relationMetadata = relationMetadata;
    this.contributorMetadata = contributorMetadata;
    this.subjectMetadata = subjectMetadata;
    this.sourceMetadata = sourceMetadata;
    this.publicationMetada = publicationMetada;
    this.languageMetadata = languageMetadata;
    this.coverageMetadata = coverageMetadata;
    this.formatMetadata = formatMetadata;
    this.rigthsMetadata = rigthsMetadata;
  }


  public Boolean getAlreadyDisseminated() {
    return this.alreadyDisseminated;
  }

  public String getAuthorsMetadata() {
    return this.authorsMetadata;
  }

  public String getContributorMetadata() {
    return this.contributorMetadata;
  }

  public String getCoverageMetadata() {
    return this.coverageMetadata;
  }

  public int getDeliverableId() {
    return this.deliverableId;
  }

  public String getDescriptionMetadata() {
    return this.descriptionMetadata;
  }

  public String getDisseminationChannel() {
    return this.disseminationChannel;
  }

  public String getDisseminationChannelName() {
    return this.disseminationChannelName;
  }

  public String getDisseminationUrl() {
    return this.disseminationUrl;
  }

  public Boolean getEffectiveDateRestriction() {
    return this.effectiveDateRestriction;
  }

  public String getFormatMetadata() {
    return this.formatMetadata;
  }

  public Integer getId() {
    return this.id;
  }

  public String getIdentifierMetadata() {
    return this.identifierMetadata;
  }

  public Boolean getIntellectualProperty() {
    return this.intellectualProperty;
  }

  public Boolean getIsOpenAccess() {
    return this.isOpenAccess;
  }

  public String getLanguageMetadata() {
    return this.languageMetadata;
  }

  public Boolean getLimitedExclusivity() {
    return this.limitedExclusivity;
  }

  public String getPublicationMetada() {
    return this.publicationMetada;
  }

  public String getPublishierMetadata() {
    return this.publishierMetadata;
  }

  public String getRelationMetadata() {
    return this.relationMetadata;
  }

  public Date getRestrictedAccessUntil() {
    return this.restrictedAccessUntil;
  }

  public Date getRestrictedEmbargoed() {
    return this.restrictedEmbargoed;
  }

  public Boolean getRestrictedUseAgreement() {
    return this.restrictedUseAgreement;
  }

  public String getRigthsMetadata() {
    return this.rigthsMetadata;
  }

  public String getSourceMetadata() {
    return this.sourceMetadata;
  }

  public String getSubjectMetadata() {
    return this.subjectMetadata;
  }

  public String getType() {
    return type;
  }

  public void setAlreadyDisseminated(Boolean alreadyDisseminated) {
    this.alreadyDisseminated = alreadyDisseminated;
  }

  public void setAuthorsMetadata(String authorsMetadata) {
    this.authorsMetadata = authorsMetadata;
  }

  public void setContributorMetadata(String contributorMetadata) {
    this.contributorMetadata = contributorMetadata;
  }

  public void setCoverageMetadata(String coverageMetadata) {
    this.coverageMetadata = coverageMetadata;
  }

  public void setDeliverableId(int deliverableId) {
    this.deliverableId = deliverableId;
  }

  public void setDescriptionMetadata(String descriptionMetadata) {
    this.descriptionMetadata = descriptionMetadata;
  }

  public void setDisseminationChannel(String disseminationChannel) {
    this.disseminationChannel = disseminationChannel;
  }

  public void setDisseminationChannelName(String disseminationChannelName) {
    this.disseminationChannelName = disseminationChannelName;
  }

  public void setDisseminationUrl(String disseminationUrl) {
    this.disseminationUrl = disseminationUrl;
  }

  public void setEffectiveDateRestriction(Boolean effectiveDateRestriction) {
    this.effectiveDateRestriction = effectiveDateRestriction;
  }

  public void setFormatMetadata(String formatMetadata) {
    this.formatMetadata = formatMetadata;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public void setIdentifierMetadata(String identifierMetadata) {
    this.identifierMetadata = identifierMetadata;
  }

  public void setIntellectualProperty(Boolean intellectualProperty) {
    this.intellectualProperty = intellectualProperty;
  }

  public void setIsOpenAccess(Boolean isOpenAccess) {
    this.isOpenAccess = isOpenAccess;
  }

  public void setLanguageMetadata(String languageMetadata) {
    this.languageMetadata = languageMetadata;
  }

  public void setLimitedExclusivity(Boolean limitedExclusivity) {
    this.limitedExclusivity = limitedExclusivity;
  }

  public void setPublicationMetada(String publicationMetada) {
    this.publicationMetada = publicationMetada;
  }

  public void setPublishierMetadata(String publishierMetadata) {
    this.publishierMetadata = publishierMetadata;
  }

  public void setRelationMetadata(String relationMetadata) {
    this.relationMetadata = relationMetadata;
  }

  public void setRestrictedAccessUntil(Date restrictedAccessUntil) {
    this.restrictedAccessUntil = restrictedAccessUntil;
  }

  public void setRestrictedEmbargoed(Date restrictedEmbargoed) {
    this.restrictedEmbargoed = restrictedEmbargoed;
  }

  public void setRestrictedUseAgreement(Boolean restrictedUseAgreement) {
    this.restrictedUseAgreement = restrictedUseAgreement;
  }

  public void setRigthsMetadata(String rigthsMetadata) {
    this.rigthsMetadata = rigthsMetadata;
  }

  public void setSourceMetadata(String sourceMetadata) {
    this.sourceMetadata = sourceMetadata;
  }

  public void setSubjectMetadata(String subjectMetadata) {
    this.subjectMetadata = subjectMetadata;
  }

  public void setType(String type) {
    this.type = type;
  }


}
