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

import org.cgiar.ccafs.ap.data.manager.impl.ProjectCofinancingLinkageManagerImpl;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.User;

import java.util.List;

import com.google.inject.ImplementedBy;


/**
 * @author Hernán David Carvajal B. - CIAT/CCAFS
 */

@ImplementedBy(ProjectCofinancingLinkageManagerImpl.class)
public interface ProjectCofinancingLinkageManager {

  /**
   * This method remove from the database the link between the core project and the bilateral projects received by
   * parameter.
   * 
   * @param coreProject - Core project identifier
   * @param linkedBilateralProjects - A list with the identifiers of the bilateral projects to be un-linked of the
   *        project.
   * @param user
   * @param justification
   * @return True if the links were deleted from the database. False otherwise.
   */
  public boolean deletedLinkedBilateralProjects(Project coreProject, List<Integer> linkedBilateralProjects, User user,
    String justification);

  /**
   * This method remove from the database the link between the bilateral project and the core projects received by
   * parameter.
   * 
   * @param project
   * @param linkedProjects - A list with the identifiers of the projects to be un-linked of the project.
   * @param user
   * @param justification
   * @return
   */
  public boolean deletedLinkedCoreProjects(Project project, List<Integer> linkedProjects, User user,
    String justification);

  /**
   * This method gets the basic information (id, title) of the projects that are linked to the project
   * identified by the value received by parameter.
   * 
   * @param projectID
   * @return a list of projects with its basic information.
   */
  public List<Project> getLinkedBilateralProjects(int projectID);

  /**
   * This method gets the basic information (id, title) of the projects that are linked to the project
   * identified by the value received by parameter.
   * 
   * @param projectID
   * @return a list of projects with its basic information.
   */
  public List<Project> getLinkedCoreProjects(int projectID);

  /**
   * This method saves into the database the bilateral projects linked to the core project received in the first
   * parameter.
   * 
   * @param coreProject - project that will be linked to some core project(s)
   * @param user - User who is making the change
   * @param justification
   * @return true if the information was saved successfully, false otherwise.
   */
  public boolean saveLinkedBilateralProjects(Project coreProject, User user, String justification);

  /**
   * This method saves into the database the core projects linked to the bilateral project received by parameter.
   * 
   * @param bilateralProject - Core project that will be linked to some bilateral project(s)
   * @param user - User who is making the change
   * @param justification
   * @return true if the information was saved successfully, false otherwise.
   */
  public boolean saveLinkedCoreProjects(Project bilateralProject, User user, String justification);
}
