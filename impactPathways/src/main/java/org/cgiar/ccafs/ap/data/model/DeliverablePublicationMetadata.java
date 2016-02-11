package org.cgiar.ccafs.ap.data.model;
// Generated Jan 20, 2016 9:03:36 AM by Hibernate Tools 4.3.1.Final

import java.util.List;

/**
 * DeliverablePublicationMetadata generated by hbm2java
 */
public class DeliverablePublicationMetadata implements java.io.Serializable {


  /**
   * @author Christian David Garcia O.
   */
  private static final long serialVersionUID = 6948869613934458902L;
  private Integer id;
  private int deliverableId;
  private String openAcessStatus;
  private Boolean isiPublication;
  private Boolean narsCoAuthor;
  private Boolean academicCoAuthor;
  private String citation;
  private Boolean acknowledgeCcafs;
  private Boolean fp1;
  private Boolean fp2;
  private Boolean fp3;
  private Boolean fp4;
  private List<IPProgram> relatedFlagships;
  private List<String> relatedFlagshipsIds;


  public DeliverablePublicationMetadata() {
  }


  public DeliverablePublicationMetadata(int deliverableId) {
    this.deliverableId = deliverableId;
  }


  public DeliverablePublicationMetadata(int deliverableId, String openAcessStatus, Boolean isiPublication,
    Boolean narsCoAuthor, Boolean academicCoAuthor, String citation, Boolean acknowledgeCcafs, Boolean fp1, Boolean fp2,
    Boolean fp3, Boolean fp4) {
    this.deliverableId = deliverableId;
    this.openAcessStatus = openAcessStatus;
    this.isiPublication = isiPublication;
    this.narsCoAuthor = narsCoAuthor;
    this.academicCoAuthor = academicCoAuthor;
    this.citation = citation;
    this.acknowledgeCcafs = acknowledgeCcafs;
    this.fp1 = fp1;
    this.fp2 = fp2;
    this.fp3 = fp3;
    this.fp4 = fp4;
  }


  public Boolean getAcademicCoAuthor() {
    return this.academicCoAuthor;
  }


  public Boolean getAcknowledgeCcafs() {
    return this.acknowledgeCcafs;
  }


  public String getCitation() {
    return this.citation;
  }

  public int getDeliverableId() {
    return this.deliverableId;
  }

  public Boolean getFp1() {

    if (fp1 == null) {
      return false;
    }
    return this.fp1;
  }

  public Boolean getFp2() {
    if (fp2 == null) {
      return false;
    }
    return this.fp2;
  }

  public Boolean getFp3() {
    if (fp3 == null) {
      return false;
    }
    return this.fp3;
  }

  public Boolean getFp4() {
    if (fp4 == null) {
      return false;
    }
    return this.fp4;
  }

  public Integer getId() {
    return this.id;
  }

  public Boolean getIsiPublication() {
    return this.isiPublication;
  }

  public Boolean getNarsCoAuthor() {
    return this.narsCoAuthor;
  }

  public String getOpenAcessStatus() {
    return this.openAcessStatus;
  }

  public List<IPProgram> getRelatedFlagships() {
    return relatedFlagships;
  }

  public List<String> getRelatedFlagshipsIds() {
    return relatedFlagshipsIds;
  }

  public void setAcademicCoAuthor(Boolean academicCoAuthor) {
    this.academicCoAuthor = academicCoAuthor;
  }

  public void setAcknowledgeCcafs(Boolean acknowledgeCcafs) {
    this.acknowledgeCcafs = acknowledgeCcafs;
  }

  public void setCitation(String citation) {
    this.citation = citation;
  }

  public void setDeliverableId(int deliverableId) {
    this.deliverableId = deliverableId;
  }

  public void setFp1(Boolean fp1) {
    this.fp1 = fp1;
  }

  public void setFp2(Boolean fp2) {
    this.fp2 = fp2;
  }

  public void setFp3(Boolean fp3) {
    this.fp3 = fp3;
  }

  public void setFp4(Boolean fp4) {
    this.fp4 = fp4;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public void setIsiPublication(Boolean isiPublication) {
    this.isiPublication = isiPublication;
  }

  public void setNarsCoAuthor(Boolean narsCoAuthor) {
    this.narsCoAuthor = narsCoAuthor;
  }

  public void setOpenAcessStatus(String openAcessStatus) {
    this.openAcessStatus = openAcessStatus;
  }

  public void setRelatedFlagships(List<IPProgram> relatedFlagships) {
    this.relatedFlagships = relatedFlagships;
  }

  public void setRelatedFlagshipsIds(List<String> relatedFlagshipsIds) {
    this.relatedFlagshipsIds = relatedFlagshipsIds;
  }


}

