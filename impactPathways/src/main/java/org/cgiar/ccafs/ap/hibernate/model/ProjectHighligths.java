package org.cgiar.ccafs.ap.hibernate.model;
// Generated Dec 2, 2015 8:36:16 AM by Hibernate Tools 3.5.0.Final


import org.cgiar.ccafs.ap.data.model.Country;
import org.cgiar.ccafs.ap.data.model.ProjectHighlightsType;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * ProjectHighligths generated by hbm2java
 */
public class ProjectHighligths implements java.io.Serializable {


  private Integer id;


  private String title;

  private String author;
  private Date startDate;
  private Date endDate;
  private String photo;
  private String objectives;
  private String description;
  private String results;
  private String partners;
  private String links;
  private String keywords;
  private String subject;
  private String contributor;
  private String publisher;
  private String relation;
  private String coverage;
  private String rights;
  private boolean isGlobal;
  private int leader;
  private Long year;
  private boolean isActive;
  private Long status;
  private long projectId;
  private Date activeSince;
  private long createdBy;
  private long modifiedBy;
  private String modificationJustification;
  private Set<ProjectHighligthsTypes> projectHighligthsTypeses = new HashSet<ProjectHighligthsTypes>(0);
  private Set<ProjectHighligthsCountry> projectHighligthsCountries = new HashSet<ProjectHighligthsCountry>(0);
  private List<ProjectHighlightsType> typesIds;
  private List<String> typesids;
  private List<Country> countries;
  private List<Integer> countriesIds;

  public ProjectHighligths() {
  }


  public ProjectHighligths(String title, String author, boolean isGlobal, int leader, boolean isActive, long projectId,
    Date activeSince, long createdBy, long modifiedBy, String modificationJustification) {
    this.title = title;
    this.author = author;
    this.isGlobal = isGlobal;
    this.leader = leader;
    this.isActive = isActive;
    this.projectId = projectId;
    this.activeSince = activeSince;
    this.createdBy = createdBy;
    this.modifiedBy = modifiedBy;
    this.modificationJustification = modificationJustification;
  }


  public ProjectHighligths(String title, String author, Date startDate, Date endDate, String photo, String objectives,
    String description, String results, String partners, String links, String keywords, String subject,
    String contributor, String publisher, String relation, String coverage, String rights, boolean isGlobal, int leader,
    Long year, boolean isActive, Long status, long projectId, Date activeSince, long createdBy, long modifiedBy,
    String modificationJustification, Set<ProjectHighligthsTypes> projectHighligthsTypeses,
    Set<ProjectHighligthsCountry> projectHighligthsCountries) {
    this.title = title;
    this.author = author;
    this.startDate = startDate;
    this.endDate = endDate;
    this.photo = photo;
    this.objectives = objectives;
    this.description = description;
    this.results = results;
    this.partners = partners;
    this.links = links;
    this.keywords = keywords;
    this.subject = subject;
    this.contributor = contributor;
    this.publisher = publisher;
    this.relation = relation;
    this.coverage = coverage;
    this.rights = rights;
    this.isGlobal = isGlobal;
    this.leader = leader;
    this.year = year;
    this.isActive = isActive;
    this.status = status;
    this.projectId = projectId;
    this.activeSince = activeSince;
    this.createdBy = createdBy;
    this.modifiedBy = modifiedBy;
    this.modificationJustification = modificationJustification;
    this.projectHighligthsTypeses = projectHighligthsTypeses;
    this.projectHighligthsCountries = projectHighligthsCountries;
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
    ProjectHighligths other = (ProjectHighligths) obj;
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

  public String getAuthor() {
    return this.author;
  }


  public String getContributor() {
    return this.contributor;
  }


  public List<Country> getCountries() {
    return countries;
  }


  public List<Integer> getCountriesIds() {
    return countriesIds;
  }


  public String getCoverage() {
    return this.coverage;
  }


  public long getCreatedBy() {
    return this.createdBy;
  }


  public String getDescription() {
    return this.description;
  }

  public Date getEndDate() {
    return this.endDate;
  }

  public Integer getId() {
    return this.id;
  }

  public String getKeywords() {
    return this.keywords;
  }

  public int getLeader() {
    return this.leader;
  }

  public String getLinks() {
    return this.links;
  }

  public String getModificationJustification() {
    return this.modificationJustification;
  }

  public long getModifiedBy() {
    return this.modifiedBy;
  }

  public String getObjectives() {
    return this.objectives;
  }

  public String getPartners() {
    return this.partners;
  }

  public String getPhoto() {
    return this.photo;
  }

  public Set<ProjectHighligthsCountry> getProjectHighligthsCountries() {
    return this.projectHighligthsCountries;
  }

  public Set<ProjectHighligthsTypes> getProjectHighligthsTypeses() {
    return this.projectHighligthsTypeses;
  }

  public long getProjectId() {
    return this.projectId;
  }

  public String getPublisher() {
    return this.publisher;
  }

  public String getRelation() {
    return this.relation;
  }

  public String getResults() {
    return this.results;
  }

  public String getRights() {
    return this.rights;
  }

  public Date getStartDate() {
    return this.startDate;
  }

  public Long getStatus() {
    return this.status;
  }

  public String getSubject() {
    return this.subject;
  }

  public String getTitle() {
    return this.title;
  }

  public List<String> getTypesids() {
    return typesids;
  }

  public List<ProjectHighlightsType> getTypesIds() {
    return typesIds;
  }

  public Long getYear() {
    return this.year;
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

  public boolean isIsGlobal() {
    return this.isGlobal;
  }

  public void setActiveSince(Date activeSince) {
    this.activeSince = activeSince;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public void setContributor(String contributor) {
    this.contributor = contributor;
  }

  public void setCountries(List<Country> countries) {
    this.countries = countries;
  }

  public void setCountriesIds(List<Integer> countriesIds) {
    this.countriesIds = countriesIds;
  }

  public void setCoverage(String coverage) {
    this.coverage = coverage;
  }

  public void setCreatedBy(long createdBy) {
    this.createdBy = createdBy;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public void setIsActive(boolean isActive) {
    this.isActive = isActive;
  }

  public void setIsGlobal(boolean isGlobal) {
    this.isGlobal = isGlobal;
  }

  public void setKeywords(String keywords) {
    this.keywords = keywords;
  }

  public void setLeader(int leader) {
    this.leader = leader;
  }

  public void setLinks(String links) {
    this.links = links;
  }

  public void setModificationJustification(String modificationJustification) {
    this.modificationJustification = modificationJustification;
  }

  public void setModifiedBy(long modifiedBy) {
    this.modifiedBy = modifiedBy;
  }

  public void setObjectives(String objectives) {
    this.objectives = objectives;
  }

  public void setPartners(String partners) {
    this.partners = partners;
  }

  public void setPhoto(String photo) {
    this.photo = photo;
  }

  public void setProjectHighligthsCountries(Set<ProjectHighligthsCountry> projectHighligthsCountries) {
    this.projectHighligthsCountries = projectHighligthsCountries;
  }

  public void setProjectHighligthsTypeses(Set<ProjectHighligthsTypes> projectHighligthsTypeses) {
    this.projectHighligthsTypeses = projectHighligthsTypeses;
  }

  public void setProjectId(long projectId) {
    this.projectId = projectId;
  }

  public void setPublisher(String publisher) {
    this.publisher = publisher;
  }

  public void setRelation(String relation) {
    this.relation = relation;
  }

  public void setResults(String results) {
    this.results = results;
  }

  public void setRights(String rights) {
    this.rights = rights;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public void setStatus(Long status) {
    this.status = status;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setTypesids(List<String> typesids) {
    this.typesids = typesids;
  }

  public void setTypesIds(List<ProjectHighlightsType> typesIds) {
    this.typesIds = typesIds;
  }

  public void setYear(Long year) {
    this.year = year;
  }


}

