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
 * ***************************************************************
 */
package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLProjectDAO;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

/**
 * @author Javier Andrés Gallego
 * @author Héctor Fabio Tobón R.
 * @author Hernán David Carvajal.
 * @author Jorge Leonardo Solis B.
 * @author Carlos Alberto Martínez M.
 */
@ImplementedBy(MySQLProjectDAO.class)
public interface ProjectDAO {

  /**
   * This method deletes the project identified by the value received by parameter.
   * 
   * @param projectID is the project identifier.
   * @param userID is the user identifier who is making the change.
   * @param justification is the justification statement.
   * @return True if the project was deleted successfully, false otherwise.
   */
  public boolean deleteProject(int projectID, int userID, String justification);

  /**
   * This method deletes from the database the relation between the project and the indicator
   * received.
   * 
   * @param projectID - project identifier
   * @param indicatorID - indicator identifier
   * @param userID - identifier of the user who is removing the project indicator
   * @param justification - indicator identifier
   * @return true if the relation was successfully removed. False otherwise.
   */
  public boolean deleteProjectIndicator(int projectID, int indicatorID, int userID, String justification);

  /**
   * This method deletes from the database the relation between the project and the output
   * received.
   * 
   * @param projectID - project identifier
   * @param outputID - output identifier
   * @param outcomeID - outcome to which the output belongs to.
   * @return true if the relation was successfully removed. False otherwise.s
   */
  public boolean deleteProjectOutput(int projectID, int outputID, int outcomeID, int userID, String justification);

  /**
   * This method validate if the project exists in the database.
   * 
   * @param projectId is the project identifier.
   * @return true if the project exists or false otherwise.
   */
  public boolean existProject(int projectId);


  /**
   * This method return all the Projects
   * 
   * @return a list of maps with the information of all Projects found.
   */

  public List<Map<String, String>> getAllProjects();

  /**
   * This method return the basic information of all projects present
   * in the database.
   * The information of the project that this method returns is:
   * ID, title and total CCAFS budget
   * 
   * @param cycle name of section
   * @reportingStratDate The start of reporting cycle
   * @return a list of maps with the information
   */
  public List<Map<String, String>> getAllProjectsBasicInfo(String cycle, Date reportingStratDate);

  /**
   * This method returns the bilateral projects that contributes with the flagship and the regions received by
   * parameter.
   * Only the projects marked as financing are returned.
   * If the parameters are '-1' they are not used to filter the list.
   * 
   * @return a list of maps with the information.
   */
  public List<Map<String, String>> getBilateralCofinancingProjects(int flagshipID, int regionID);

  /**
   * This method returns a list with all the bilateral projects.
   * 
   * @return a list of maps with the information.
   */
  public List<Map<String, String>> getBilateralProjects();


  /**
   * This method returns a list with all the bilateral projects with a valid project Leader.
   * 
   * @return a list of maps with the information.
   */
  public List<Map<String, String>> getBilateralProjectsLeaders();


  /**
   * This method returns the core projects that contributes with the flagship and the regions received by parameter.
   * If the parameters are '-1' they are not used to filter the list.
   * 
   * @return a list of maps with the information.
   */
  public List<Map<String, String>> getCoreProjects(int flagshipID, int regionID);


  /**
   * This method returns the core projects with a valid Project Leader that contributes with the flagship and the
   * regions received by parameter.
   * If the parameters are '-1' they are not used to filter the list.
   * 
   * @return a list of maps with the information.
   */
  public List<Map<String, String>> getCoreProjectsLeaders(int flagshipID, int regionID);

  /**
   * This method returns a list of project identifiers where the user is assigned as Project Leader or Project
   * Coordinator.
   * 
   * @param userID is the user identifier.
   * @return a list of Integers representing the project identifiers.
   */
  public List<Integer> getPLProjectIds(int userID);

  /**
   * This method return the project information of the selected list
   * indicated by parameter.
   * 
   * @param projectID, identifier of the project selected
   * @return a list of maps with the information of the Project returned.
   */
  public Map<String, String> getProject(int projectID);

  /**
   * This method return the basic information of the project identified by
   * the value received as parameter
   * The information of the project that this method returns is:
   * ID, title and total CCAFS budget
   * 
   * @return a list of maps with the information
   */
  public Map<String, String> getProjectBasicInfo(int projectID);

  /**
   * Get a Project Coordinator information with a given Project ID
   * 
   * @param ProjectID is the id of a project
   * @return a Map with the project coordinator information or an empty map if no user found. If an error occurs, a NULL
   *         will be returned.
   */
  public Map<String, String> getProjectCoordinator(int projectID);


  /**
   * This method returns the list of the projects evaluation
   * but only with the basic information:
   * ID, title, the regions and flagships to which the project contributes
   * and the total budget, is_submited and total score
   * 
   * @param year the evaluating year
   * @param roleId the role id
   * @param userId the user id
   * @param reportingStratDate the date start reporting
   * @param ipProgramId the progam id
   * @return a list with Project objects.
   */
  public List<Map<String, String>> getProjectEvaluationInfo(int year, int roleId, int userId, Date reportingStratDate,
    int ipProgramId);

  /**
   * this method returns the project id in which the given activity belongs to.
   * 
   * @param activityID is the activity identifier.
   * @return an integer representing the project id, or -1 if the activity identifier does not belong to any project.
   */
  public int getProjectIdFromActivityId(int activityID);

  /**
   * this method returns the project id in which the given deliverable belongs to.
   * 
   * @param deliverableID is the deliverable identifier.
   * @return an integer representing the project id, or -1 if the deliverable identifier does not belong to any project.
   */
  public int getProjectIdFromDeliverableId(int deliverableID);

  /**
   * This method gets the project ID where the given partner belongs to.
   * 
   * @param projectPartnerID is a partner identifier.
   * @return a integer representing the id of the project, or -1 if no project was found.
   */
  public int getProjectIDFromProjectPartnerID(int projectPartnerID);

  /**
   * This method returns a list of project identifiers that can be edited by a given user.
   * An user can edit a project if:
   * - Is the management liaison contact person
   * - It is affiliated to the liaison institution organization of the project
   * - Is the leader of the project
   * - If the project is bilateral and you are a focal point (CP or ML) of the lead institution
   * - Has the administrator role
   * 
   * @param userId is the owner identifier.
   * @return a list of Integers which represent the project identifiers.
   */
  public List<Integer> getProjectIdsEditables(int userId);


  /**
   * Get a Project Leader information with a given Project Id
   * 
   * @param ProjectId is the id of a project
   * @return a Map with the project leader information or an empty map if no user found. If an error occurs, a NULL will
   *         be returned.
   * @deprecated Deprecated as of P&R v2.1, replaced by ProjectPartnerDAO.getProjectPartners(int projectId, String
   *             projectPartnerType).
   */
  @Deprecated
  public Map<String, String> getProjectLeader(int projectID);

  /**
   * This method return a list with the employees that belongs to a program
   * indicated by parameter.
   * 
   * @param programID, identifier of the program
   * @return a list of maps with the information of Employees returned.
   */
  public List<Map<String, String>> getProjectOwnerContact(int programId);

  /**
   * This method return the Program which belongs to the logged user
   * indicated by parameter.
   * 
   * @param userID, identifier of the program
   * @return a list of maps with the information of the program returned.
   */
  public List<Map<String, String>> getProjectOwnerId(int programId);

  /**
   * This method returns a list of projects that belongs to an institution id given as parameter.
   * 
   * @param institutionID, Object identifier of the institution
   * @return a list of maps which represent the projects.
   */
  public List<Map<String, String>> getProjectsByInstitution(Object institutionID);

  /**
   * This method return all the Projects which belongs to the program
   * indicated by the parameter.
   * 
   * @param programID is identifier of the program
   * @return a list of maps with the information of all Projects found.
   */

  public List<Map<String, String>> getProjectsByProgram(int programId);

  /**
   * This method gets all the projects that belongs to a given user related with an institution
   * 
   * @param institutionId - is the id of the institution
   * @param userId - is the id of the user
   * @return a list of map of projects related with the user and institution.
   */
  public List<Map<String, String>> getProjectsOwning(int institutionId, int userId);

  /**
   * this method add or update an expected project leader that belongs to a specific project.
   * 
   * @param projectId is the id of the project where the expected project leader belongs to.
   * @param expectedProjectLeaderData is the expected project leader data.
   * @return the id of the new expected project leader, 0 if there was an update of the data and -1 if some error
   *         happened.
   */
  public int saveExpectedProjectLeader(int projectId, Map<String, Object> expectedProjectLeaderData);

  /**
   * This method saves the Project information given by the user
   * indicated by parameter.
   * 
   * @param programID, identifier of the program
   * @return if the operation succeed or not.
   */
  public int saveProject(Map<String, Object> projectData);

  /**
   * This method save into the database the relation between a project and
   * one output
   * 
   * @param outputData - information to be saved
   * @return The last inserted id if there was a new record, 0 if the record was updated or -1 if any error happened.
   */
  public int saveProjectOutput(Map<String, String> outputData);

  /**
   * This method returns the information of all the active projects to be used in the XML report
   * 
   * @return a list of Map with the information requested, or an empty List if nothing found. Or null if some error
   *         occurs.
   */
  public List<Map<String, Object>> summaryGetActiveProjects();

  /**
   * This method returns the information of all the activities by project and their gender contribution to be used in
   * the summary report of the same name
   * 
   * @param termsToSearch
   * @return a list of Map with the information requested, or an empty List if nothing found. Or null if some error
   *         occurs.
   */

  public List<Map<String, Object>> summaryGetAllActivitiesWithGenderContribution(String[] termsToSearch);

  /**
   * This method returns the information of all project CCAFS Outcomes with their information to be used in
   * the summary report of Impact Pathway summary.
   * 
   * @param year year of to search the indicators
   * @return a list of Map with the information requested, or an empty List if nothing found. Or null if some error
   *         occurs.
   */
  public List<Map<String, Object>> summaryGetAllCCAFSOutcomes(int year);

  /**
   * This method returns the information of all the deliverable with their next users to be used in the summary report
   * of gender summary.
   * 
   * @return a list of Map with the information requested, or an empty List if nothing found. Or null if some error
   *         occurs.
   */

  public List<Map<String, Object>> summaryGetAllDeliverablesWithGenderContribution(String[] termsToSearch);

  /**
   * This method returns the information of all the projects with their partner leaders to be used in the summary report
   * of
   * project partner leaders.
   * 
   * @param year he current planning year to generate the report budget figures
   * @return a list of Map with the information requested, or an empty List if nothing found. Or null if some error
   *         occurs.
   */
  public List<Map<String, Object>> summaryGetAllProjectPartnerLeaders(int year);

  /**
   * This method returns the information of all the deliverables and their projects to be used in the summary report of
   * expected deliverables.
   * 
   * @return a list of Map with the information requested, or an empty List if nothing found. Or null if some error
   *         occurs.
   */
  public List<Map<String, Object>> summaryGetAllProjectsWithDeliverables();

  /**
   * This method returns the information of all the projects and their gender contribution to be used in the summary
   * report of
   * the same name.
   * 
   * @return a list of Map with the information requested, or an empty List if nothing found. Or null if some error
   *         occurs.
   */

  public List<Map<String, Object>> summaryGetAllProjectsWithGenderContribution(String[] termsToSearch);

  /**
   * This method returns the information of all project MOG with your budget by Year and their information to be used in
   * the summary report of POWBMOGs summary.
   * 
   * @param year
   * @return a list of Map with the information requested, or an empty List if nothing found. Or null if some error
   *         occurs.
   */
  public List<Map<String, Object>> summaryGetInformationDetailPOWB(int year);


  /**
   * This method returns the information of all project MOG with your budget by Year and their information to be used in
   * the summary report of POWBMOGs summary.
   * 
   * @param year year to search
   * @return a list of Map with the information requested, or an empty List if nothing found. Or null if some error
   *         occurs.
   */
  public List<Map<String, Object>> summaryGetInformationPOWB(int year);

  /**
   * This method returns the information of all project with your budget by partner of the currentPlanningYear their
   * information to be used in
   * the summary report of Budget By Partners summary.
   * 
   * @return a list of Map with the information requested, or an empty List if nothing found. Or null if some error
   *         occurs.
   */
  public List<Map<String, Object>> summaryGetProjectBudgetPerPartners(int year);

  /**
   * This method returns the information of all project submmmited with the information for the summary report of
   * Submmited summary.
   * 
   * @param year year to search
   * @return a list of Map with the information requested, or an empty List if nothing found. Or null if some error
   *         occurs.
   */
  public List<Map<String, Object>> summaryGetProjectsNotModified();

  /**
   * This method returns the information of all project submmmited with the information for the summary report of
   * Submmited summary.
   * 
   * @param year year to search submitted projects
   * @param cycle cycle for to search submitted projects
   * @return a list of Map with the information requested, or an empty List if nothing found. Or null if some error
   *         occurs.
   */
  public List<Map<String, Object>> summaryGetProjectSubmmited(int year, String cycle);

  /**
   * This method updates the project cofinancing into the database according to the values received by parameter.
   * 
   * @param projectID - project identifier
   * @param cofinancing -true if is cofinancing
   * @return true if the change was made successfully. False otherwise.
   */
  public boolean updateProjectCofinancing(int projectID, boolean cofinancing);

  /**
   * This method updates the project type into the database according to the values received by parameter.
   * 
   * @param projectID - project identifier
   * @param type - project type
   * @return true if the change was made successfully. False otherwise.
   */
  public boolean updateProjectType(int projectID, String type);

  /**
   * This method updates the type of all the core projects following the steps below:
   * 1- Set the projects co-founded as core
   * 2- Check all the "core" projects that have at least one link with a bilateral project and update its type to
   * co-founded
   * 
   * @return true if the changes was made successfully. False otherwise.
   */
  public boolean updateProjectTypes();
}
