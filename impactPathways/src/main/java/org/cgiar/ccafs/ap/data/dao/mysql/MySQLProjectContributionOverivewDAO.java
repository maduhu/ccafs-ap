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

package org.cgiar.ccafs.ap.data.dao.mysql;

import org.cgiar.ccafs.ap.data.dao.ProjectContributionOverivewDAO;
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
 * @author Hernán David Carvajal B. - CIAT/CCAFS
 */

public class MySQLProjectContributionOverivewDAO implements ProjectContributionOverivewDAO {

  private static Logger LOG = LoggerFactory.getLogger(MySQLProjectContributionOverivewDAO.class);
  private DAOManager daoManager;

  @Inject
  public MySQLProjectContributionOverivewDAO(DAOManager daoManager) {
    this.daoManager = daoManager;
  }

  @Override
  public boolean deleteProjectContributionOverview(int outputOverviewID, int userID, String justification) {
    String query = "UPDATE ip_project_contribution_overviews SET is_active = FALSE, ";
    query += "modified_by = ?, modification_justification = ? ";
    query += "WHERE id = ?";

    int result = daoManager.delete(query, new String[] {userID + "", justification, outputOverviewID + ""});
    return (result != -1);
  }

  @Override
  public List<Map<String, String>> getProjectContributionOverviews(int projectID) {
    List<Map<String, String>> overviewsData = new ArrayList<>();
    StringBuilder query = new StringBuilder();
    query.append("SELECT distinct ipco.id, ipco.year, ipco.anual_contribution, ipco.gender_contribution, ");
    query.append("ie.id as 'output_id', ie.description as output_description,ipco.brief_summary ,ipco.summary_gender ");
    query.append("FROM ip_project_contributions ipc ");
    query.append(
      "INNER JOIN ip_project_contribution_overviews ipco ON ipco.output_id = ipc.mog_id  and ipc.project_id=ipco.project_id");
    query.append(" INNER JOIN ip_elements ie ON ipc.mog_id = ie.id ");
    query.append("WHERE ipc.project_id = ");
    query.append(projectID);

    try (Connection con = daoManager.getConnection()) {
      ResultSet rs = daoManager.makeQuery(query.toString(), con);
      while (rs.next()) {
        Map<String, String> overview = new HashMap<>();
        overview.put("id", rs.getString("id"));
        overview.put("year", rs.getString("year"));
        overview.put("annual_contribution", rs.getString("anual_contribution"));
        overview.put("gender_contribution", rs.getString("gender_contribution"));
        overview.put("output_id", rs.getString("output_id"));
        overview.put("output_description", rs.getString("output_description"));
        overview.put("brief_summary", rs.getString("brief_summary"));
        overview.put("summary_gender", rs.getString("summary_gender"));

        overviewsData.add(overview);
      }

    } catch (SQLException e) {
      LOG.error("-- getProjectContributionOverviews(int projectID)( ) > Exception arised trying to get the project "
        + "contributions overview for project {}", projectID, e);
    }
    return overviewsData;
  }


  @Override
  public List<Map<String, String>> getProjectContributionOverviewsByYearAndOutput(int projectID, int year,
    int outputID) {
    List<Map<String, String>> overviewsData = new ArrayList<>();
    StringBuilder query = new StringBuilder();
    query.append("SELECT ipco.id, ipco.year, ipco.anual_contribution, ipco.gender_contribution, ");
    query.append("ie.id as 'output_id', ie.description as output_description,ipco.brief_summary ,ipco.summary_gender ");
    query.append("FROM ip_project_contributions ipc ");
    query.append("INNER JOIN ip_project_contribution_overviews ipco ON ipco.output_id = ipc.mog_id ");
    query.append("INNER JOIN ip_elements ie ON ipc.mog_id = ie.id ");
    query.append("WHERE ipc.project_id = ");
    query.append(projectID);
    query.append(" AND ipco.year = ");
    query.append(year);
    query.append(" AND ipco.output_id = ");
    query.append(outputID);

    try (Connection con = daoManager.getConnection()) {
      ResultSet rs = daoManager.makeQuery(query.toString(), con);
      while (rs.next()) {
        Map<String, String> overview = new HashMap<>();
        overview.put("id", rs.getString("id"));
        overview.put("year", rs.getString("year"));
        overview.put("annual_contribution", rs.getString("anual_contribution"));
        overview.put("gender_contribution", rs.getString("gender_contribution"));
        overview.put("output_id", rs.getString("output_id"));
        overview.put("output_description", rs.getString("output_description"));
        overview.put("brief_summary", rs.getString("brief_summary"));
        overview.put("summary_gender", rs.getString("summary_gender"));

        overviewsData.add(overview);
      }

    } catch (SQLException e) {
      LOG.error("-- getProjectContributionOverviews(int projectID)( ) > Exception arised trying to get the project "
        + "contributions overview for project {}", projectID, e);
    }
    return overviewsData;
  }

  @Override
  public List<Map<String, String>> getProjectContributionOverviewsSynthesis(int mogId, int year, int program) {
    List<Map<String, String>> overviewsData = new ArrayList<>();
    StringBuilder query = new StringBuilder();
    query.append("select * from ip_project_contribution_overviews where YEAR= ");
    query.append(year + " and output_id=" + mogId
      + " and is_active=1 and project_id in (select project_id from project_focuses where program_id=" + program + ")");

    try (Connection con = daoManager.getConnection()) {
      ResultSet rs = daoManager.makeQuery(query.toString(), con);
      while (rs.next()) {
        Map<String, String> overview = new HashMap<>();
        overview.put("id", rs.getString("id"));
        overview.put("year", rs.getString("year"));
        overview.put("annual_contribution", rs.getString("anual_contribution"));
        overview.put("gender_contribution", rs.getString("gender_contribution"));
        overview.put("output_id", rs.getString("output_id"));
        overview.put("project_id", rs.getString("project_id"));
        overview.put("brief_summary", rs.getString("brief_summary"));
        overview.put("summary_gender", rs.getString("summary_gender"));

        overviewsData.add(overview);
      }

    } catch (SQLException e) {
      LOG.error("-- getProjectContributionOverviews(int projectID)( ) > Exception arised trying to get the project "
        + "contributions overview for project {}", e);
    }
    return overviewsData;
  }


  @Override
  public List<Map<String, String>> getProjectContributionOverviewsSynthesisGlobal(int mogId, int year, int program) {
    List<Map<String, String>> overviewsData = new ArrayList<>();
    StringBuilder query = new StringBuilder();
    query.append("select * from ip_project_contribution_overviews where YEAR= ");
    query.append(year + " and output_id=" + mogId
      + " and is_active=1 and project_id in (select id from projects where is_global=1)");

    try (Connection con = daoManager.getConnection()) {
      ResultSet rs = daoManager.makeQuery(query.toString(), con);
      while (rs.next()) {
        Map<String, String> overview = new HashMap<>();
        overview.put("id", rs.getString("id"));
        overview.put("year", rs.getString("year"));
        overview.put("annual_contribution", rs.getString("anual_contribution"));
        overview.put("gender_contribution", rs.getString("gender_contribution"));
        overview.put("output_id", rs.getString("output_id"));
        overview.put("project_id", rs.getString("project_id"));
        overview.put("brief_summary", rs.getString("brief_summary"));
        overview.put("summary_gender", rs.getString("summary_gender"));

        overviewsData.add(overview);
      }

    } catch (SQLException e) {
      LOG.error("-- getProjectContributionOverviews(int projectID)( ) > Exception arised trying to get the project "
        + "contributions overview for project {}", e);
    }
    return overviewsData;
  }

  @Override
  public boolean saveProjectContribution(int projectID, Map<String, Object> overviewData, int userID,
    String justification) {

    StringBuilder query = new StringBuilder();
    Object[] values = null;
    if (overviewData.get("id") == null) {

      query.append("INSERT INTO ip_project_contribution_overviews (project_id, output_id, year, ");
      query.append(
        "anual_contribution, gender_contribution, created_by, modified_by, modification_justification,brief_summary,summary_gender) ");
      query.append("VALUES ( ?, ?, ?, ?, ?, ?, ?, ?,?,?) ");


      values = new Object[10];
      values[0] = projectID;
      values[1] = overviewData.get("output_id");
      values[2] = overviewData.get("year");
      values[3] = overviewData.get("annual_contribution");
      values[4] = overviewData.get("gender_contribution");
      values[5] = userID;
      values[6] = userID;
      values[7] = justification;
      values[8] = overviewData.get("brief_summary");
      values[9] = overviewData.get("summary_gender");


    } else {


      query.append("update ip_project_contribution_overviews set project_id=?, output_id=?, year=?, ");
      query.append(
        "anual_contribution=?, gender_contribution=?, created_by=?, modified_by=?, modification_justification=?,brief_summary=?,summary_gender =? where id=?");
      values = new Object[11];
      values[0] = projectID;
      values[1] = overviewData.get("output_id");
      values[2] = overviewData.get("year");
      values[3] = overviewData.get("annual_contribution");
      values[4] = overviewData.get("gender_contribution");
      values[5] = userID;
      values[6] = userID;
      values[7] = justification;
      values[8] = overviewData.get("brief_summary");
      values[9] = overviewData.get("summary_gender");

      values[10] = overviewData.get("id");

    }


    int result = daoManager.saveData(query.toString(), values);
    return result != -1;
  }

}
