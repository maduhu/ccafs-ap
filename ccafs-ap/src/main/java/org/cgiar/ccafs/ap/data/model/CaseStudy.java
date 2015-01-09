package org.cgiar.ccafs.ap.data.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;


public class CaseStudy extends Deliverable {

  private int id;
  private String title;
  private String author;
  private Date startDate;
  private Date endDate;
  private File image;
  private String imageContentType;
  private String imageFileName;
  private String objectives;
  private String description;
  private String results;
  private String partners;
  private String links;
  private String keywords;
  private List<Country> countries;
  private Logframe logframe;
  private Leader leader;
  private boolean isGlobal;
  private List<CaseStudyType> types;

  public CaseStudy() {
  }

  public CaseStudy(int id) {
    this.id = id;
  }

  public String getAuthor() {
    return author;
  }

  public List<Country> getCountries() {
    return countries;
  }

  public List<String> getCountriesIds() {
    ArrayList<String> ids = new ArrayList<>();
    for (int c = 0; c < getCountries().size(); c++) {
      ids.add(getCountries().get(c).getId() + "");
    }
    return ids;
  }

  public String getDescription() {
    return description;
  }

  public Date getEndDate() {
    return endDate;
  }

  public int getId() {
    return id;
  }

  public File getImage() {
    return image;
  }

  public String getImageContentType() {
    return imageContentType;
  }

  public String getImageFileName() {
    return imageFileName;
  }

  public String getKeywords() {
    return keywords;
  }

  public Leader getLeader() {
    return leader;
  }

  public String getLinks() {
    return links;
  }

  public Logframe getLogframe() {
    return logframe;
  }

  public String getObjectives() {
    return objectives;
  }

  public String getPartners() {
    return partners;
  }

  public String getResults() {
    return results;
  }

  public Date getStartDate() {
    return startDate;
  }

  public String getTitle() {
    return title;
  }

  public List<CaseStudyType> getTypes() {
    return types;
  }

  public List<String> getTypesIds() {
    ArrayList<String> ids = new ArrayList<>();
    for (int c = 0; c < getTypes().size(); c++) {
      ids.add(getTypes().get(c).getId() + "");
    }
    return ids;
  }

  public boolean isGlobal() {
    return isGlobal;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public void setCountries(List<Country> countries) {
    this.countries = countries;
  }

  public void setDescription(String description) {
    this.description = description;
  }


  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }


  public void setGlobal(boolean isGlobal) {
    this.isGlobal = isGlobal;
  }


  public void setId(int id) {
    this.id = id;
  }


  public void setImage(File image) {
    this.image = image;
  }

  public void setImageContentType(String photoContentType) {
    this.imageContentType = photoContentType;
  }

  public void setImageFileName(String photoFileName) {
    this.imageFileName = photoFileName;
  }

  public void setKeywords(String keywords) {
    this.keywords = keywords;
  }

  public void setLeader(Leader leader) {
    this.leader = leader;
  }

  public void setLinks(String links) {
    this.links = links;
  }

  public void setLogframe(Logframe logframe) {
    this.logframe = logframe;
  }

  public void setObjectives(String objectives) {
    this.objectives = objectives;
  }

  public void setPartners(String partners) {
    this.partners = partners;
  }

  public void setResults(String results) {
    this.results = results;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setTypes(List<CaseStudyType> types) {
    this.types = types;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

}
