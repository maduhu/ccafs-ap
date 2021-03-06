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

package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLBudgetOverheadDAO;

import java.util.Map;

import com.google.inject.ImplementedBy;


/**
 * @author Hernán David Carvajal B. - CIAT/CCAFS
 */

@ImplementedBy(MySQLBudgetOverheadDAO.class)
public interface BudgetOverheadDAO {

  /**
   * This method get the project budget overhead data for the project identified by the value received by parameter.
   * 
   * @param projectID - Project identifier
   * @return a Map with the information.
   */
  public Map<String, String> getProjectBudgetOverhead(int projectID);

  /**
   * This method saves the project budget overhead information into the database.
   * 
   * @param projectData - A map with the information to save.
   * @return - True if the information was saved successfully. False otherwise.
   */
  public boolean saveProjectBudgetOverhead(Map<String, Object> projectData);
}
