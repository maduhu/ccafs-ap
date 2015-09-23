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

import org.cgiar.ccafs.ap.data.dao.ProjectStatusDAO;
import org.cgiar.ccafs.utils.db.DAOManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Héctor Fabio Tobón R. - CIAT/CCAFS
 */

public class MySQLProjectStatusDAO implements ProjectStatusDAO {

  // Logger
  private static Logger LOG = LoggerFactory.getLogger(MySQLProjectStatusDAO.class);

  private DAOManager databaseManager;

  @Inject
  public MySQLProjectStatusDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  @Override
  public Map<String, String> getProjectStatus(int projectID, String cycle, String section) {
    LOG.debug(">> getProjectStatus projectID = {} and cycle = {} )", new Object[] {projectID, cycle});

    StringBuilder query = new StringBuilder();
    query.append("SELECT * ");
    query.append("FROM project_statuses ");
    query.append("WHERE project_id = ");
    query.append(projectID);
    query.append(" AND cycle = '");
    query.append(cycle);
    query.append("' AND section_name = '");
    query.append(section);
    query.append("'");

    LOG.debug(">> getProjectStatus() > Calling method executeQuery to get the results");

    Map<String, String> statusData = new HashMap<>();

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      if (rs.next()) {
        statusData.put("id", rs.getString("id"));
        statusData.put("project_id", rs.getString("project_id"));
        statusData.put("cycle", rs.getString("cycle"));
        statusData.put("section_name", rs.getString("section_name"));
        statusData.put("missing_fields", rs.getString("missing_fields"));
      }
      rs.close();
      rs = null; // For the garbage collector to find it easily.
    } catch (SQLException e) {
      String exceptionMessage = "-- executeQuery() > Exception raised trying ";
      exceptionMessage += "to execute the following query " + query;
      LOG.error(exceptionMessage, e);
      return null;
    }
    LOG.debug("<< getProjectStatus() > Calling method executeQuery to get the results");
    return statusData;
  }

  @Override
  public int saveProjectStatus(Map<String, Object> statusData) {
    LOG.debug(">> saveProjectStatus(statusData={})", new Object[] {statusData});
    StringBuilder query = new StringBuilder();
    Object[] values;
    int result = -1;
    if (statusData.get("id") == null) {
      query.append("INSERT INTO project_statuses (project_id, cycle, section_name, missing_fields) ");
      query.append("VALUES (?, ?, ?, ?) ");
      values = new Object[4];
      values[0] = statusData.get("project_id");;
      values[1] = statusData.get("cycle");
      values[2] = statusData.get("section_name");
      values[3] = statusData.get("missing_fields");
      result = databaseManager.saveData(query.toString(), values);
      if (result <= 0) {
        LOG.error("A problem happened trying to add a new project_status for the project_id={}",
          statusData.get("project_id"));
      }
    } else {
      // Updating submission record.
      query.append("UPDATE project_statuses SET project_id = ?, cycle = ?, section_name = ?, missing_fields = ? ");
      query.append("WHERE id = ? ");
      values = new Object[5];
      values[0] = statusData.get("project_id");
      values[1] = statusData.get("cycle");
      values[2] = statusData.get("section_name");
      values[3] = statusData.get("missing_fields");
      values[4] = statusData.get("id");
      result = databaseManager.saveData(query.toString(), values);
      if (result == -1) {
        LOG.error("A problem happened trying to update the project_status identified with the id = {}",
          statusData.get("id"));
      }
    }
    return result;
  }

}
