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
package org.cgiar.ccafs.ap.data.dao.mysql;

import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.dao.ProjectDAO;
import org.cgiar.ccafs.ap.data.model.BudgetType;
import org.cgiar.ccafs.utils.db.DAOManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Héctor Fabio Tobón R.
 * @author Javier Andrés Gallego.
 * @author Hernán David Carvajal B.
 */
public class MySQLProjectDAO implements ProjectDAO {

  // Logger
  private static Logger LOG = LoggerFactory.getLogger(MySQLProjectDAO.class);

  private DAOManager databaseManager;

  @Inject
  public MySQLProjectDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  @Override
  public boolean deleteProject(int projectID) {
    LOG.debug(">> deleteProject(projectID={})", projectID);

    String query = "UPDATE projects SET is_active = 0 WHERE id = ?";

    int rowsUpdated = databaseManager.saveData(query, new Object[] {projectID});
    if (rowsUpdated >= 0) {
      LOG.debug("<< deleteProject():{}", true);
      return true;
    }

    LOG.debug("<< deleteProject:{}", false);
    return false;
  }

  @Override
  public boolean deleteProjectIndicator(int projectID, int indicatorID, int userID, String justification) {
    LOG.debug(">> deleteProjectIndicator(projectID={}, indicatorID={})", projectID, indicatorID);

    StringBuilder query = new StringBuilder();
    query.append("UPDATE ip_project_indicators SET is_active = FALSE, ");
    query.append("modified_by = ? , modification_justification = ? ");
    query.append("WHERE project_id = ? AND id = ? ");

    Object[] values = new Object[4];
    values[0] = userID;
    values[1] = justification;
    values[2] = projectID;
    values[3] = indicatorID;

    int rowsDeleted = databaseManager.delete(query.toString(), values);
    if (rowsDeleted >= 0) {
      LOG.debug("<< deleteProjectIndicator():{}", true);
      return true;
    }

    LOG.debug("<< deleteProjectIndicator:{}", false);
    return false;
  }


  @Override
  public boolean deleteProjectOutput(int projectID, int outputID, int outcomeID, int userID, String justification) {
    LOG.debug(">> deleteProjectOutput(projectID={}, outputID={})", projectID, outputID);

    StringBuilder query = new StringBuilder();
    query.append("UPDATE ip_project_contributions SET is_active = FALSE, ");
    query.append("modified_by = ?, modification_justification = ? ");
    query.append("WHERE project_id = ? AND mog_id = ? AND midOutcome_id = ?; ");

    Object[] values = new Object[5];
    values[0] = userID;
    values[1] = justification;
    values[2] = projectID;
    values[3] = outputID;
    values[4] = outcomeID;

    int rowsDeleted = databaseManager.delete(query.toString(), values);
    if (rowsDeleted >= 0) {
      LOG.debug("<< deleteProjectOutput():{}", true);
      return true;
    }

    LOG.debug("<< deleteProjectOutput:{}", false);
    return false;
  }


  @Override
  public boolean existProject(int projectID) {
    LOG.debug(">> existProject projectID = {} )", projectID);
    StringBuilder query = new StringBuilder();
    query.append("SELECT COUNT(id) FROM projects WHERE id = ");
    query.append(projectID);
    boolean exists = false;
    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      if (rs.next()) {
        if (rs.getInt(1) > 0) {
          exists = true;
        }
      }
      con.close();
    } catch (SQLException e) {
      LOG.error("Exception arised getting the project id.", projectID, e);
    }
    LOG.debug("-- existProject() > Calling method executeQuery to get the results");
    return exists;
  }

  @Override
  public List<Map<String, String>> getAllProjects() {
    LOG.debug(">> getAllProjects )");
    StringBuilder query = new StringBuilder();

    query.append("SELECT p.* ");
    query.append("FROM projects as p ");
    query.append("INNER JOIN employees emp ON emp.id = p.liaison_user_id ");
    query.append("INNER JOIN institutions i ON i.id = emp.institution_id ");
    query.append("INNER JOIN ip_programs ip ON ip.id = i.program_id ");

    LOG.debug("-- getAllProjects() > Calling method executeQuery to get the results");
    return this.getData(query.toString());
  }

  @Override
  public List<Map<String, String>> getAllProjectsBasicInfo() {
    LOG.debug(">> getAllProjectsBasicInfo( )");
    List<Map<String, String>> projectList = new ArrayList<>();
    StringBuilder query = new StringBuilder();

    String regionsSubquery =
      "SELECT GROUP_CONCAT(ipp.acronym) "
        + "FROM ip_programs ipp INNER JOIN project_focuses pf ON ipp.id = pf.program_id "
        + "WHERE pf.project_id = p.id AND ipp.type_id = " + APConstants.REGION_PROGRAM_TYPE;

    String flagshipsSubquery =
      "SELECT GROUP_CONCAT(ipp.acronym) "
        + "FROM ip_programs ipp INNER JOIN project_focuses pf ON ipp.id = pf.program_id "
        + "WHERE pf.project_id = p.id AND ipp.type_id = " + APConstants.FLAGSHIP_PROGRAM_TYPE;

    query.append("SELECT p.id, p.title, p.type, p.active_since, SUM(b.amount) as 'total_budget_amount', ");
    query.append("( " + regionsSubquery + " )  as 'regions', ");
    query.append("( " + flagshipsSubquery + " )  as 'flagships' ");
    query.append("FROM projects as p ");
    query.append("LEFT JOIN project_budgets pb ON p.id = pb.project_id ");
    query.append("LEFT JOIN budgets b ON pb.budget_id = b.id AND b.budget_type IN ( ");
    query.append(BudgetType.W1_W2.getValue() + ", ");
    query.append(BudgetType.W3_BILATERAL.getValue() + " ) ");
    query.append("WHERE p.is_active = TRUE ");
    query.append("GROUP BY p.id");

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      while (rs.next()) {
        Map<String, String> projectData = new HashMap<String, String>();
        projectData.put("id", rs.getString("id"));
        projectData.put("title", rs.getString("title"));
        projectData.put("type", rs.getString("type"));
        projectData.put("total_budget_amount", rs.getString("total_budget_amount"));
        projectData.put("created", rs.getTimestamp("active_since").getTime() + "");
        projectData.put("regions", rs.getString("regions"));
        projectData.put("flagships", rs.getString("flagships"));
        projectList.add(projectData);
      }
      rs.close();
    } catch (SQLException e) {
      String exceptionMessage = "-- getAllProjectsBasicInfo() > Exception raised trying ";
      exceptionMessage += "to get the basic information of the projects " + query;

      LOG.error(exceptionMessage, e);
    }


    LOG.debug("-- getAllProjectsBasicInfo() : projectList.size={} ", projectList.size());
    return projectList;
  }


  @Override
  public List<Map<String, String>> getBilateralCofinancingProjects(int flagshipID, int regionID) {
    LOG.debug(">> getBilateralCofinancingProjects (flagshipID={}, regionID={})", flagshipID, regionID);
    List<Map<String, String>> coreProjects = new ArrayList<>();

    StringBuilder query = new StringBuilder();
    query.append("SELECT p.id, p.title ");
    query.append("FROM projects as p ");
    query.append("WHERE p.type = '");
    query.append(APConstants.PROJECT_BILATERAL_STANDALONE);
    query.append("' AND is_cofinancing = TRUE ");

    if (flagshipID != -1) {
      query.append(" AND p.id IN (SELECT project_id FROM project_focuses WHERE program_id = ");
      query.append(flagshipID);
      query.append(") ");
    }

    if (regionID != -1) {
      query.append(" AND p.id IN (SELECT project_id FROM project_focuses WHERE program_id = ");
      query.append(regionID);
      query.append(") ");
    }

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      while (rs.next()) {
        Map<String, String> projectData = new HashMap<>();
        projectData.put("id", rs.getString("id"));
        projectData.put("title", rs.getString("title"));

        coreProjects.add(projectData);
      }

    } catch (SQLException e) {
      LOG.error("getCoreProjects() > Exception raised trying to get the core projects.", e);
    }

    return coreProjects;
  }

  @Override
  public List<Map<String, String>> getCoreProjects(int flagshipID, int regionID) {
    LOG.debug(">> getCoreProjects (flagshipID={}, regionID={})", flagshipID, regionID);
    List<Map<String, String>> coreProjects = new ArrayList<>();

    StringBuilder query = new StringBuilder();
    query.append("SELECT p.id, p.title ");
    query.append("FROM projects as p ");
    query.append("WHERE p.type = '");
    query.append(APConstants.PROJECT_CORE);
    query.append("' ");

    if (flagshipID != -1) {
      query.append(" AND p.id IN (SELECT project_id FROM project_focuses WHERE program_id = ");
      query.append(flagshipID);
      query.append(") ");
    }

    if (regionID != -1) {
      query.append(" AND p.id IN (SELECT project_id FROM project_focuses WHERE program_id = ");
      query.append(regionID);
      query.append(") ");
    }

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      while (rs.next()) {
        Map<String, String> projectData = new HashMap<>();
        projectData.put("id", rs.getString("id"));
        projectData.put("title", rs.getString("title"));

        coreProjects.add(projectData);
      }

    } catch (SQLException e) {
      LOG.error("getCoreProjects() > Exception raised trying to get the core projects.", e);
    }

    return coreProjects;
  }

  private List<Map<String, String>> getData(String query) {
    LOG.debug(">> executeQuery(query='{}')", query);
    List<Map<String, String>> projectList = new ArrayList<>();
    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query, con);
      while (rs.next()) {
        Map<String, String> projectData = new HashMap<String, String>();
        projectData.put("id", rs.getString("id"));
        projectData.put("title", rs.getString("title"));
        projectData.put("summary", rs.getString("summary"));
        if (rs.getDate("start_date") != null) {
          projectData.put("start_date", rs.getDate("start_date").toString());
        }
        if (rs.getDate("end_date") != null) {
          projectData.put("end_date", rs.getDate("end_date").toString());
        }
        projectData.put("project_leader_id", rs.getString("project_leader_id"));
        projectData.put("liaison_institution_id", rs.getString("liaison_institution_id"));
        projectData.put("liaison_user_id", rs.getString("liaison_user_id"));
        projectData.put("created", rs.getTimestamp("active_since").getTime() + "");

        projectList.add(projectData);
      }
      rs.close();
    } catch (SQLException e) {
      String exceptionMessage = "-- executeQuery() > Exception raised trying ";
      exceptionMessage += "to execute the following query " + query;

      LOG.error(exceptionMessage, e);
    }

    LOG.debug("<< executeQuery():ProjectList.size={}", projectList.size());
    return projectList;
  }

  @Override
  public Map<String, String> getExpectedProjectLeader(int projectID) {
    LOG.debug(">> getExpectedProjectLeader(projectID={})", projectID);
    Map<String, String> expectedProjectLeaderData = new HashMap<>();
    try (Connection connection = databaseManager.getConnection()) {
      StringBuilder query = new StringBuilder();
      query.append("SELECT epl.* ");
      query.append("FROM expected_project_leaders epl ");
      query.append("INNER JOIN projects p ON p.expected_project_leader_id = epl.id ");
      query.append("WHERE p.id= ");
      query.append(projectID);

      ResultSet rs = databaseManager.makeQuery(query.toString(), connection);
      if (rs.next()) {
        expectedProjectLeaderData.put("id", rs.getString("id"));
        expectedProjectLeaderData.put("contact_first_name", rs.getString("contact_first_name"));
        expectedProjectLeaderData.put("contact_last_name", rs.getString("contact_last_name"));
        expectedProjectLeaderData.put("contact_email", rs.getString("contact_email"));
        expectedProjectLeaderData.put("institution_id", rs.getString("institution_id"));
      }
      rs.close();
    } catch (SQLException e) {
      LOG.error("-- getExpectedProjectLeader() > There was an error getting the data for expeted project leader {}.",
        projectID, e);
      return null;
    }
    LOG.debug("<< getExpectedProjectLeader():{}", expectedProjectLeaderData);
    return expectedProjectLeaderData;
  }

  @Override
  public List<Integer> getPLProjectIds(int userID) {
    LOG.debug(">> getPLProjectIds(employeeId={})", new Object[] {userID});
    List<Integer> projectIds = new ArrayList<>();
    try (Connection connection = databaseManager.getConnection()) {
      StringBuilder query = new StringBuilder();
      query.append("SELECT project_id FROM project_partners pp WHERE (pp.partner_type = '");
      query.append(APConstants.PROJECT_PARTNER_PL);
      query.append("' or pp.partner_type = '");
      query.append(APConstants.PROJECT_PARTNER_PC);
      query.append("') AND pp.user_id = ");
      query.append(userID);
      ResultSet rs = databaseManager.makeQuery(query.toString(), connection);
      while (rs.next()) {
        projectIds.add(rs.getInt(1));
      }
      rs.close();
    } catch (SQLException e) {
      LOG.error("-- getPLProjectIds() > There was an error getting the data for employeeId={}.", new Object[] {userID},
        e.getMessage());
      return null;
    }
    LOG.debug("<< getPLProjectIds():{}", projectIds);
    return projectIds;
  }

  @Override
  public Map<String, String> getProject(int projectID) {
    LOG.debug(">> getProject projectID = {} )", projectID);
    Map<String, String> projectData = new HashMap<String, String>();
    StringBuilder query = new StringBuilder();
    query.append("SELECT p.*, u.id as 'owner_id', i.id as 'owner_institution_id'");
    query.append("FROM projects as p ");
    query.append("INNER JOIN liaison_users lu ON p.liaison_user_id = lu.id ");
    query.append("INNER JOIN users u ON lu.user_id = u.id ");
    query.append("INNER JOIN liaison_institutions li ON p.liaison_institution_id = li.id  ");
    query.append("INNER JOIN institutions i ON li.institution_id = i.id ");
    query.append("WHERE p.id = ");
    query.append(projectID);
    query.append(" AND p.is_active = 1");

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      if (rs.next()) {
        projectData.put("id", rs.getString("id"));
        projectData.put("title", rs.getString("title"));
        projectData.put("type", rs.getString("type"));
        projectData.put("summary", rs.getString("summary"));
        projectData.put("is_cofinancing", rs.getString("is_cofinancing"));
        if (rs.getDate("start_date") != null) {
          projectData.put("start_date", rs.getDate("start_date").toString());
        }
        if (rs.getDate("end_date") != null) {
          projectData.put("end_date", rs.getDate("end_date").toString());
        }
        // projectData.put("project_leader_id", rs.getString("project_leader_id"));
        projectData.put("liaison_institution_id", rs.getString("liaison_institution_id"));
        projectData.put("liaison_user_id", rs.getString("owner_id"));
        projectData.put("requires_workplan_upload", rs.getString("requires_workplan_upload"));
        // projectData.put("project_owner_institution_id", rs.getString("owner_institution_id"));
        projectData.put("created", rs.getTimestamp("active_since").getTime() + "");
      }
      con.close();
    } catch (SQLException e) {
      LOG.error("Exception arised getting the project with id {}.", projectID, e);
    }
    LOG.debug("-- getProject() > Calling method executeQuery to get the results");
    return projectData;
  }

  @Override
  public Map<String, String> getProjectBasicInfo(int projectID) {
    LOG.debug(">> getProjectBasicInfo( id={} )", projectID);
    Map<String, String> projectData = new HashMap<String, String>();
    StringBuilder query = new StringBuilder();

    query.append("SELECT p.id, p.title, p.active_since, SUM(b.amount) as 'total_budget_amount', ");
    query.append("GROUP_CONCAT( DISTINCT ipp1.acronym ) as 'regions', ");
    query.append("GROUP_CONCAT( DISTINCT ipp2.acronym ) as 'flagships' ");
    query.append("FROM projects as p ");
    query.append("LEFT JOIN project_budgets pb ON p.id = pb.project_id ");
    query.append("LEFT JOIN budgets b ON pb.budget_id = b.id ");
    query.append("LEFT JOIN project_focuses pf ON p.id = pf.project_id ");
    query.append("LEFT JOIN ip_programs ipp1 ON pf.program_id = ipp1.id AND ipp1.type_id = ");
    query.append(APConstants.REGION_PROGRAM_TYPE);
    query.append(" LEFT JOIN ip_programs ipp2 ON pf.program_id = ipp2.id AND ipp2.type_id = ");
    query.append(APConstants.FLAGSHIP_PROGRAM_TYPE);
    query.append(" WHERE p.id = " + projectID);
    query.append(" GROUP BY p.id  ");

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      while (rs.next()) {
        projectData.put("id", rs.getString("id"));
        projectData.put("title", rs.getString("title"));
        projectData.put("total_budget_amount", rs.getString("total_budget_amount"));
        projectData.put("created", rs.getTimestamp("active_since").getTime() + "");
        projectData.put("regions", rs.getString("regions"));
        projectData.put("flagships", rs.getString("flagships"));
      }
      rs.close();
    } catch (SQLException e) {
      String exceptionMessage = "-- getAllProjectsBasicInfo() > Exception raised trying ";
      exceptionMessage += "to get the basic information of the projects " + query;

      LOG.error(exceptionMessage, e);
    }


    LOG.debug("-- getAllProjectsBasicInfo() : projectList.size={} ", projectData.size());
    return projectData;
  }

  @Override
  public Map<String, String> getProjectCoordinator(int projectID) {
    LOG.debug(">> getProjectCoordinator(projectID={})", projectID);
    Map<String, String> projectCoordinatorData = new HashMap<>();
    try (Connection connection = databaseManager.getConnection()) {

      StringBuilder query = new StringBuilder();
      query.append("SELECT u.id, u.first_name, u.last_name, u.email, i.id as 'institution_id'");
      query.append("FROM project_partners pp ");
      query.append("INNER JOIN users u ON u.id = pp.user_id ");
      query.append("INNER JOIN institutions i ON i.id = pp.partner_id ");
      query.append("AND pp.partner_type = '" + APConstants.PROJECT_PARTNER_PC + "' ");
      query.append("AND pp.project_id = ");
      query.append(projectID);

      ResultSet rs = databaseManager.makeQuery(query.toString(), connection);
      if (rs.next()) {
        projectCoordinatorData.put("id", rs.getString("id"));
        projectCoordinatorData.put("first_name", rs.getString("first_name"));
        projectCoordinatorData.put("last_name", rs.getString("last_name"));
        projectCoordinatorData.put("email", rs.getString("email"));
        projectCoordinatorData.put("institution_id", rs.getString("institution_id"));
      }
      rs.close();
    } catch (SQLException e) {
      LOG.error("-- getProjectCoordinator() > There was an error getting the data for user {}.", projectID, e);
      return null;
    }
    LOG.debug("<< getProjectCoordinator():{}", projectCoordinatorData);
    return projectCoordinatorData;
  }

  @Override
  public int getProjectIdFromActivityId(int activityID) {
    LOG.debug(">> getProjectIdFromActivityId(activityID={})", new Object[] {activityID});
    int projectID = -1;
    try (Connection connection = databaseManager.getConnection()) {
      StringBuilder query = new StringBuilder();
      query.append("SELECT a.project_id FROM activities a WHERE a.id = ");
      query.append(activityID);
      ResultSet rs = databaseManager.makeQuery(query.toString(), connection);
      while (rs.next()) {
        projectID = rs.getInt(1);
      }
      rs.close();
    } catch (SQLException e) {
      LOG.error("-- getProjectIdFromActivityId() > There was an error getting the data for activityID={}.",
        new Object[] {activityID}, e.getMessage());
    }
    LOG.debug("<< getProjectIdFromActivityId(): projectID={}", projectID);
    return projectID;
  }

  @Override
  public int getProjectIdFromDeliverableId(int deliverableID) {
    LOG.debug(">> getProjectIdFromDeliverableId(deliverableID={})", new Object[] {deliverableID});
    int projectID = -1;
    try (Connection connection = databaseManager.getConnection()) {
      StringBuilder query = new StringBuilder();
      query.append("SELECT d.project_id FROM deliverables d WHERE d.id = ");
      query.append(deliverableID);
      ResultSet rs = databaseManager.makeQuery(query.toString(), connection);
      while (rs.next()) {
        projectID = rs.getInt(1);
      }
      rs.close();
    } catch (SQLException e) {
      LOG.error("-- getProjectIdFromDeliverableId() > There was an error getting the data for deliverableID={}.",
        new Object[] {deliverableID}, e.getMessage());
    }
    LOG.debug("<< getProjectIdFromDeliverableId(): projectID={}", projectID);
    return projectID;
  }

  @Override
  public List<Integer> getProjectIdsEditables(int userID) {
    LOG.debug(">> getProjectIdsEditables(projectID={}, ownerId={})", userID);
    List<Integer> projectIds = new ArrayList<>();
    try (Connection connection = databaseManager.getConnection()) {
      StringBuilder query = new StringBuilder();
      query.append("SELECT p.id FROM projects p WHERE ");
      query.append("p.liaison_user_id = (SELECT id FROM liaison_users WHERE user_id =  ");
      query.append(userID);
      query.append(") OR p.liaison_institution_id = (SELECT institution_id FROM liaison_users WHERE user_id = ");
      query.append(userID);
      query.append(") OR EXISTS (SELECT project_id FROM project_partners WHERE  partner_type = '");
      query.append(APConstants.PROJECT_PARTNER_PL);
      query.append("' AND project_id = p.id AND user_id = ");
      query.append(userID);
      query.append(") ");
      query.append("AND p.is_active = TRUE ");
      ResultSet rs = databaseManager.makeQuery(query.toString(), connection);
      while (rs.next()) {
        projectIds.add(rs.getInt(1));
      }
      rs.close();
    } catch (SQLException e) {
      LOG
        .error("-- getProjectIdsEditables() > Exception raised getting the projects editables for user {}.", userID, e);
    }
    LOG.debug("<< getProjectIdsEditables():{}", projectIds);
    return projectIds;
  }

  @Override
  public List<Map<String, String>> getProjectIndicators(int projectID) {
    LOG.debug(">> getProjectIndicators( projectID = {} )", projectID);
    List<Map<String, String>> indicatorsDataList = new ArrayList<>();

    StringBuilder query = new StringBuilder();
    query.append("SELECT ai.id, ai.description, ai.target, ai.year, aip.id as 'parent_id', ");
    query.append("aip.description as 'parent_description', aip.target as 'parent_target', ");
    query.append("ie.id as 'outcome_id', ie.description as 'outcome_description' ");
    query.append("FROM ip_project_indicators as ai ");
    query.append("INNER JOIN ip_indicators aip ON ai.parent_id = aip.id ");
    query.append("INNER JOIN ip_elements ie ON ai.outcome_id = ie.id ");
    query.append("WHERE ai.project_id=  ");
    query.append(projectID);
    query.append(" AND ai.is_active = TRUE ");

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      while (rs.next()) {
        Map<String, String> indicatorData = new HashMap<String, String>();

        indicatorData.put("id", rs.getString("id"));
        indicatorData.put("description", rs.getString("description"));
        indicatorData.put("target", rs.getString("target"));
        indicatorData.put("year", rs.getString("year"));
        indicatorData.put("parent_id", rs.getString("parent_id"));
        indicatorData.put("parent_description", rs.getString("parent_description"));
        indicatorData.put("parent_target", rs.getString("parent_target"));
        indicatorData.put("outcome_id", rs.getString("outcome_id"));
        indicatorData.put("outcome_description", rs.getString("outcome_description"));

        indicatorsDataList.add(indicatorData);
      }
      rs.close();
    } catch (SQLException e) {
      String exceptionMessage = "-- getProjectIndicators() > Exception raised trying ";
      exceptionMessage += "to get the activity indicators for activity  " + projectID;

      LOG.error(exceptionMessage, e);
      return null;
    }
    LOG.debug("<< getProjectIndicators():indicatorsDataList.size={}", indicatorsDataList.size());
    return indicatorsDataList;
  }

  @Override
  public Map<String, String> getProjectLeader(int projectID) {
    LOG.debug(">> getProjectLeader(projectID={})", projectID);
    Map<String, String> projectLeaderData = new HashMap<>();
    try (Connection connection = databaseManager.getConnection()) {

      StringBuilder query = new StringBuilder();
      query.append("SELECT u.id, u.first_name, u.last_name, u.email, i.id as 'institution_id'");
      query.append("FROM project_partners pp ");
      query.append("INNER JOIN users u ON u.id = pp.user_id ");
      query.append("INNER JOIN institutions i ON i.id = pp.partner_id ");
      query.append("AND pp.partner_type = '" + APConstants.PROJECT_PARTNER_PL + "' ");
      query.append("AND pp.project_id = ");
      query.append(projectID);

      ResultSet rs = databaseManager.makeQuery(query.toString(), connection);
      if (rs.next()) {
        projectLeaderData.put("id", rs.getString("id"));
        projectLeaderData.put("first_name", rs.getString("first_name"));
        projectLeaderData.put("last_name", rs.getString("last_name"));
        projectLeaderData.put("email", rs.getString("email"));
        projectLeaderData.put("institution_id", rs.getString("institution_id"));
        // projectLeaderData.put("employee_id", rs.getString("employee_id")); NOT used any more.
      }
      rs.close();
    } catch (SQLException e) {
      LOG.error("-- getProjectLeader() > There was an error getting the data for user {}.", projectID, e);
      return null;
    }
    LOG.debug("<< getProjectLeader():{}", projectLeaderData);
    return projectLeaderData;
  }

  @Override
  public List<Map<String, String>> getProjectOwnerContact(int institutionId) {
    LOG.debug(">> getProjectOwnerContact( programID = {} )", institutionId);

    StringBuilder query = new StringBuilder();
    query.append("SELECT p.*   ");
    query.append("FROM `ccafs_employees` as ce ");
    // query.append("INNER JOIN users u ON u.id = ce.users_id ");
    // query.append("INNER JOIN persons p ON p.id = u.person_id ");
    query.append("INNER JOIN persons p ON p.id = u.person_id ");
    query.append("WHERE ce.institution_id='1' ");
    // query.append(institutionId);


    LOG.debug("-- getProjectOwnerContact() > Calling method executeQuery to get the results");
    return this.getData(query.toString());
  }

  @Override
  public List<Map<String, String>> getProjectOwnerId(int programId) {
    LOG.debug(">> getProjectOwnerId( programID = {} )", programId);

    StringBuilder query = new StringBuilder();
    query.append("SELECT p.*   ");
    query.append("FROM `projects` as p ");
    query.append("INNER JOIN project_focuses pf ON p.id = pf.project_id ");
    query.append("INNER JOIN ip_programs ipr    ON pf.program_id=ipr.id ");
    query.append("WHERE ipr.id='1' ");
    // query.append(programID);


    LOG.debug("-- getProjectOwnerId() > Calling method executeQuery to get the results");
    return this.getData(query.toString());
  }

  @Override
  public List<Map<String, String>> getProjectsByProgram(int programID) {
    LOG.debug(">> getProjects programID = {} )", programID);
    StringBuilder query = new StringBuilder();

    query.append("SELECT p.* ");
    query.append("FROM projects as p ");
    query.append("INNER JOIN employees emp ON emp.id = p.liaison_user_id ");
    query.append("INNER JOIN institutions i ON i.id = emp.institution_id ");
    query.append("INNER JOIN ip_programs ip ON ip.id = i.program_id ");
    query.append("WHERE ip.id = ");
    query.append(programID);

    LOG.debug("-- getProjects() > Calling method executeQuery to get the results");
    return this.getData(query.toString());
  }

  @Override
  public List<Map<String, String>> getProjectsOwning(int institutionId, int userId) {
    List<Map<String, String>> projectList = new ArrayList<>();
    LOG.debug(">> getProjectsOwning institutionId = {} )", institutionId);

    StringBuilder query = new StringBuilder();
    query.append("SELECT p.*, emp.user_id as 'owner_user_id', emp.institution_id as 'owner_institution_id' ");
    query.append("FROM projects p ");
    query.append("INNER JOIN employees emp ON p.liaison_user_id=emp.id ");
    query.append("WHERE emp.user_id= ");
    query.append(userId);
    query.append(" AND emp.institution_id= ");
    query.append(institutionId);

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      while (rs.next()) {
        Map<String, String> projectData = new HashMap<String, String>();
        projectData.put("id", rs.getString("id"));
        projectData.put("title", rs.getString("title"));
        projectData.put("summary", rs.getString("summary"));
        if (rs.getDate("start_date") != null) {
          projectData.put("start_date", rs.getDate("start_date").toString());
        }
        if (rs.getDate("end_date") != null) {
          projectData.put("end_date", rs.getDate("end_date").toString());
        }
        projectData.put("project_leader_id", rs.getString("project_leader_id"));
        projectData.put("liaison_institution_id", rs.getString("liaison_institution_id"));
        projectData.put("liaison_user_id", rs.getString("liaison_user_id"));
        projectData.put("project_owner_user_id", rs.getString("owner_user_id"));
        projectData.put("project_owner_institution_id", rs.getString("owner_institution_id"));
        projectData.put("created", rs.getTimestamp("active_since").getTime() + "");

        projectList.add(projectData);
      }
      rs.close();
    } catch (SQLException e) {
      String exceptionMessage = "-- executeQuery() > Exception raised trying ";
      exceptionMessage += "to execute the following query " + query;

      LOG.error(exceptionMessage, e);
    }

    LOG.debug("<< executeQuery():ProjectList.size={}", projectList.size());
    return projectList;
  }

  @Override
  public int saveExpectedProjectLeader(int projectId, Map<String, Object> expectedProjectLeaderData) {
    LOG.debug(">> saveExpectedProjectLeader(projectData={})", projectId);
    StringBuilder query = new StringBuilder();
    int result = -1;
    int newId = -1;
    if (expectedProjectLeaderData.get("id") == null) {
      // Add the record into the database and assign it to the projects table (column expected_project_leader_id).
      query
        .append("INSERT INTO expected_project_leaders (contact_first_name, contact_last_name, contact_email, institution_id) ");
      query.append("VALUES (?, ?, ?, ?) ");
      Object[] values = new Object[4];
      values[0] = expectedProjectLeaderData.get("contact_first_name");
      values[1] = expectedProjectLeaderData.get("contact_last_name");
      values[2] = expectedProjectLeaderData.get("contact_email");
      values[3] = expectedProjectLeaderData.get("institution_id");
      newId = databaseManager.saveData(query.toString(), values);
      if (newId <= 0) {
        LOG.error("A problem happened trying to add a new expected project leader in project with id={}", projectId);
        return -1;
      } else {
        // Now we need to assign the new id in the table projects (column expected_project_leader_id).
        query.setLength(0); // Clearing query.
        query.append("UPDATE projects SET expected_project_leader_id = ");
        query.append(newId);
        query.append(" WHERE id = ");
        query.append(projectId);

        try (Connection conn = databaseManager.getConnection()) {
          result = databaseManager.makeChange(query.toString(), conn);
          if (result == 0) {
            // Great!, record was updated.
            result = newId;
          }
        } catch (SQLException e) {
          LOG.error("error trying to create a connection to the database. ", e);
          return -1;
        }
      }
    } else {
      // UPDATE the record into the database.
      query
        .append("UPDATE expected_project_leaders SET contact_first_name = ?, contact_last_name = ?, contact_email = ?, institution_id = ? ");
      query.append("WHERE id = ?");
      Object[] values = new Object[5];
      values[0] = expectedProjectLeaderData.get("contact_first_name");
      values[1] = expectedProjectLeaderData.get("contact_last_name");
      values[2] = expectedProjectLeaderData.get("contact_email");
      values[3] = expectedProjectLeaderData.get("institution_id");
      values[4] = expectedProjectLeaderData.get("id");
      result = databaseManager.saveData(query.toString(), values);
      if (result == -1) {
        LOG.error("A problem happened trying to update an expected project leader identified with the id = {}",
          expectedProjectLeaderData.get("id"));
        return -1;
      }
    }
    return result;
  }

  @Override
  public int saveProject(Map<String, Object> projectData) {
    LOG.debug(">> saveProject(projectData={})", projectData);
    int result = -1;
    StringBuilder query = new StringBuilder();
    if (projectData.get("id") == null) {
      // Insert a new project record.
      query.append("INSERT INTO projects ");
      query.append("(liaison_user_id, liaison_institution_id, created_by, ");
      query.append(" modified_by, modification_justification, type) ");
      query.append("VALUES ((SELECT id FROM liaison_users WHERE user_id = ?), ?, ?, ?, ?, ?) ");
      Object[] values = new Object[6];
      values[0] = projectData.get("user_id");
      values[1] = projectData.get("liaison_institution_id");
      values[2] = projectData.get("created_by");
      values[3] = projectData.get("created_by");
      values[4] = " ";
      values[5] = projectData.get("type");
      result = databaseManager.saveData(query.toString(), values);
      LOG.debug("<< saveProject():{}", result);

    } else {
      // Update project.
      query.append("UPDATE projects SET title = ?, summary = ?, start_date = ?, end_date = ?, ");
      query.append("liaison_user_id = (SELECT id FROM liaison_users WHERE user_id = ?), is_cofinancing = ?, ");
      query.append("requires_workplan_upload = ?, liaison_institution_id = ?, type = ?, modified_by = ?, ");
      query.append("modification_justification = ? WHERE id = ?");
      Object[] values = new Object[12];
      values[0] = projectData.get("title");
      values[1] = projectData.get("summary");
      values[2] = projectData.get("start_date");
      values[3] = projectData.get("end_date");
      values[4] = projectData.get("user_id");
      values[5] = projectData.get("is_cofinancing");
      values[6] = projectData.get("requires_workplan_upload");
      values[7] = projectData.get("liaison_institution_id");
      values[8] = projectData.get("type");
      values[9] = projectData.get("modified_by");
      values[10] = projectData.get("justification");
      values[11] = projectData.get("id");
      result = databaseManager.saveData(query.toString(), values);
    }
    LOG.debug(">> saveProject(projectData={})", projectData);
    return result;
  }

  @Override
  public boolean saveProjectIndicators(Map<String, String> indicatorData) {
    LOG.debug(">> saveProjectIndicators(indicatorData={})", indicatorData);
    StringBuilder query = new StringBuilder();


    Object[] values;
    // Insert new activity indicator record
    query.append("INSERT INTO ip_project_indicators (id, description, target, year, project_id, ");
    query.append("parent_id, outcome_id, created_by, modified_by, modification_justification) ");
    query.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");
    query.append("ON DUPLICATE KEY UPDATE is_active = TRUE, ");
    query.append("description = VALUES(description), target = VALUES(target), ");
    query.append("modified_by = VALUES(modified_by), ");
    query.append("modification_justification = VALUES(modification_justification) ");

    values = new Object[10];
    values[0] = indicatorData.get("id");
    values[1] = indicatorData.get("description");
    values[2] = indicatorData.get("target");
    values[3] = indicatorData.get("year");
    values[4] = indicatorData.get("project_id");
    values[5] = indicatorData.get("parent_id");
    values[6] = indicatorData.get("outcome_id");
    values[7] = indicatorData.get("user_id");
    values[8] = indicatorData.get("user_id");
    values[9] = indicatorData.get("justification");

    int newId = databaseManager.saveData(query.toString(), values);
    if (newId == -1) {
      LOG
        .warn(
          "-- saveProjectIndicators() > A problem happened trying to add a new project indicator. Data tried to save was: {}",
          indicatorData);
      LOG.debug("<< saveProjectIndicators(): {}", false);
      return false;
    }

    LOG.debug("<< saveProjectIndicators(): {}", true);
    return true;
  }

  @Override
  public int saveProjectOutput(Map<String, String> outputData) {
    LOG.debug(">> saveProjectOutput(outputData={})", outputData);
    StringBuilder query = new StringBuilder();

    Object[] values;
    // Insert new activity indicator record
    query.append("INSERT INTO ip_project_contributions ");
    query.append("(project_id, mog_id, midOutcome_id, created_by, modified_by, modification_justification) ");
    query.append("VALUES (?, ?, ?, ?, ?, ?) ");
    query.append("ON DUPLICATE KEY UPDATE is_active = TRUE, ");
    query.append("project_id = VALUES(project_id), mog_id = VALUES(mog_id), midOutcome_id = VALUES(midOutcome_id), ");
    query.append("modified_by = VALUES(modified_by), modification_justification = VALUES(modification_justification)");

    values = new Object[6];
    values[0] = outputData.get("project_id");
    values[1] = outputData.get("mog_id");
    values[2] = outputData.get("midOutcome_id");
    values[3] = outputData.get("user_id");
    values[4] = outputData.get("user_id");
    values[5] = outputData.get("justification");

    int newId = databaseManager.saveData(query.toString(), values);
    if (newId == -1) {
      LOG.warn(
        "-- saveProjectOutput() > A problem happened trying to add a new project output. Data tried to save was: {}",
        outputData);
      LOG.debug("<< saveProjectOutput(): {}", -1);
      return -1;
    }

    LOG.debug("<< saveProjectOutput(): {}", newId);
    return newId;
  }

  @Override
  public boolean updateProjectIndicators(Map<String, String> indicatorData) {
    LOG.debug(">> updateProjectIndicators(indicatorData={})", indicatorData);
    StringBuilder query = new StringBuilder();
    Object[] values;


    // Insert new activity indicator record
    query.append("UPDATE ip_project_indicators SET ");
    query.append("modified_by = ? , modification_justification = ?, ");
    query.append("description = ?, target = ? ");
    query.append("WHERE id = ? ");

    values = new Object[5];
    values[0] = indicatorData.get("user_id");
    values[1] = indicatorData.get("justification");
    values[2] = indicatorData.get("description");
    values[3] = indicatorData.get("target");
    values[4] = indicatorData.get("id");

    int newId = databaseManager.saveData(query.toString(), values);
    if (newId == -1) {
      LOG.warn("-- updateProjectIndicators() > A problem happened trying to update the project indicator {}.",
        indicatorData.get("id"));
      LOG.debug("<< updateProjectIndicators(): {}", false);
      return false;
    }

    LOG.debug("<< updateProjectIndicators(): {}", true);
    return true;
  }

}
