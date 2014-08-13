/*****************************************************************
 * This file is part of CCAFS Planning and Reporting Platform.
 * CCAFS P&R is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * at your option) any later version.
 * CCAFS P&R is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with CCAFS P&R. If not, see <http://www.gnu.org/licenses/>.
 *****************************************************************/
package org.cgiar.ccafs.ap.data.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * This class represents a Project.
 * 
 * @author Javier Andrés Gallego
 * @author Héctor Fabio Tobón R
 */
public class Project {

  private int id;
  private String title;
  private String summary;
  private Date startDate;
  private Date endDate;
  private List<IPProgram> regions; // The list of regions in which this project works with.
  private List<IPProgram> flagships; // The list of flagships in which this project works with.
  private User leader; // Project leader will be a user too.
  private String leaderResponsabilities;
  private IPProgram programCreator; // Creator program. e.g. LAM, FP4, CU, etc.
  private User owner;
  private List<ProjectPartner> projectPartners; // Project partners.
  private User expectedLeader;
  private List<Budget> budgets;
  private ProjectOutcome outcome;
  private List<Activity> activities;
  private long created; // Timestamp number when the project was created

  public Project() {

  }

  public Project(int id) {
    this.id = id;
  }

  /**
   * Equals based on the the project id.
   */
  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Project) {
      Project p = (Project) obj;
      return p.getId() == this.getId();
    }
    return super.equals(obj);
  }

  public List<Activity> getActivities() {
    return activities;
  }

  /**
   * This method calculates all the years between the start date and the end date.
   * 
   * @return a List of numbers representing all the years, or an empty list if nothing found.
   */
  public List<Integer> getAllYears() {
    List<Integer> allYears = new ArrayList<>();
    if (startDate != null && endDate != null) {
      Calendar calendarStart = Calendar.getInstance();
      calendarStart.setTime(startDate);
      Calendar calendarEnd = Calendar.getInstance();
      calendarEnd.setTime(endDate);

      while (calendarStart.getTimeInMillis() <= calendarEnd.getTimeInMillis()) {
        // Adding the year to the list.
        allYears.add(calendarStart.get(Calendar.YEAR));
        // Adding a year (365 days) to the start date.
        calendarStart.add(Calendar.YEAR, 1);
      }
    }

    return allYears;
  }

  public List<Budget> getBudgets() {
    return budgets;
  }

  /**
   * This method returns a composed Identifier that is going to be used in the front-end.
   * The convention is going to be used depending on the creationg date of the project.
   * yyyy-project.id => e.g. 2014-46
   * 
   * @return the composed indentifier or null if the created date is null.
   */
  public String getComposedId() {
    if (created != 0) {
      Calendar calendar = Calendar.getInstance();
      calendar.setTimeInMillis(created);
      return calendar.get(Calendar.YEAR) + "-" + this.id;
    }
    return null;
  }

  public long getCreated() {
    return created;
  }

  public Date getEndDate() {
    return endDate;
  }

  public User getExpectedLeader() {
    return expectedLeader;
  }

  public List<IPProgram> getFlagships() {
    return flagships;
  }

  public String getFlagshipsAcronym() {
    StringBuilder flagshipAcronym = new StringBuilder();

    for (int i = 0; i < flagships.size(); i++) {
      flagshipAcronym.append(flagships.get(i).getAcronym());
      if (i != (flagships.size() - 1)) {
        flagshipAcronym.append(", ");
      }
    }
    return flagshipAcronym.toString();
  }

  public int getId() {
    return id;
  }

  public User getLeader() {
    return leader;
  }

  public String getLeaderResponsabilities() {
    return leaderResponsabilities;
  }

  public ProjectOutcome getOutcome() {
    return outcome;
  }

  public User getOwner() {
    return owner;
  }

  public IPProgram getProgramCreator() {
    return programCreator;
  }

  public List<ProjectPartner> getProjectPartners() {
    return projectPartners;
  }

  public List<IPProgram> getRegions() {
    return regions;
  }

  public String getRegionsAcronym() {
    StringBuilder regionAcronym = new StringBuilder();

    for (int i = 0; i < regions.size(); i++) {
      regionAcronym.append(regions.get(i).getAcronym());
      if (i != (regions.size() - 1)) {
        regionAcronym.append(", ");
      }
    }
    return regionAcronym.toString();
  }

  public Date getStartDate() {
    return startDate;
  }

  public String getSummary() {
    return summary;
  }

  public String getTitle() {
    return title;
  }

// TODO Calcular TotalCcafsBudget sin leveraged budget y TotalOverrallBudget con leveraged budget
  public double getTotalCcafsBudget() {
    double totalBudget = 0.0;
    for (Budget budget : this.getBudgets()) {
      totalBudget += budget.getAmount();
    }
    return totalBudget;
  }

  public List<IPProgram> getTypes() {
    return regions;
  }

  @Override
  public int hashCode() {
    return this.getId();
  }

  public void setActivities(List<Activity> activities) {
    this.activities = activities;
  }

  public void setBudgets(List<Budget> budgets) {
    this.budgets = budgets;
  }

  public void setCreated(long created) {
    this.created = created;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  public void setExpectedLeader(User expectedLeader) {
    this.expectedLeader = expectedLeader;
  }

  public void setFlagships(List<IPProgram> flagships) {
    this.flagships = flagships;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setLeader(User leader) {
    this.leader = leader;
  }

  public void setLeaderResponsabilities(String leaderResponsabilities) {
    this.leaderResponsabilities = leaderResponsabilities;
  }

  public void setOutcome(ProjectOutcome outcome) {
    this.outcome = outcome;
  }

  public void setOwner(User owner) {
    this.owner = owner;
  }

  public void setProgramCreator(IPProgram creator) {
    this.programCreator = creator;
  }

  public void setProjectPartners(List<ProjectPartner> projectPartners) {
    this.projectPartners = projectPartners;
  }

  public void setRegions(List<IPProgram> regions) {
    this.regions = regions;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public void setSummary(String summary) {
    this.summary = summary;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setTypes(List<IPProgram> types) {
    this.regions = types;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}