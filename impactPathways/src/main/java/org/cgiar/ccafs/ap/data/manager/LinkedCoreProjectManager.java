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

package org.cgiar.ccafs.ap.data.manager;

import org.cgiar.ccafs.ap.data.manager.impl.LinkedCoreProjectManagerImpl;
import org.cgiar.ccafs.ap.data.model.Project;

import java.util.List;

import com.google.inject.ImplementedBy;


/**
 * @author Hernán David Carvajal B. - CIAT/CCAFS
 */

@ImplementedBy(LinkedCoreProjectManagerImpl.class)
public interface LinkedCoreProjectManager {

  /**
   * This method gets the basic information (id, title) of the projects that are linked to the bilateral project
   * identified by the value received by parameter.
   * 
   * @param projectID
   * @return a list of projects with its basic information.
   */
  public List<Project> getLinkedCoreProjects(int projectID);

  /**
   * This method saves into the database the core projects linked to the bilateral project identified by the value
   * received by parameter.
   * 
   * @param project - Bilateral project that will be linked to some core project(s)
   * @return true if the information was saved successfully, false otherwise.
   */
  public boolean saveLinkedCoreProjects(Project project);
}