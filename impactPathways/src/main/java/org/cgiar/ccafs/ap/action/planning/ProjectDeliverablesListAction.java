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
package org.cgiar.ccafs.ap.action.planning;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.DeliverableManager;
import org.cgiar.ccafs.ap.data.manager.DeliverableTypeManager;
import org.cgiar.ccafs.ap.data.manager.ProjectManager;
import org.cgiar.ccafs.ap.data.model.Deliverable;
import org.cgiar.ccafs.ap.data.model.DeliverableType;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.utils.APConfig;

import java.util.List;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Javier Andrés Gallego B.
 * @author Héctor Fabio Tobón R.
 * @author Hernán David Carvajal B.
 */
public class ProjectDeliverablesListAction extends BaseAction {

  private static final long serialVersionUID = -6143944536558245482L;

  // LOG
  private static Logger LOG = LoggerFactory.getLogger(ProjectDeliverablesListAction.class);

  // Manager
  private DeliverableManager deliverableManager;
  private DeliverableTypeManager deliverableTypeManager;
  private ProjectManager projectManager;

  // Model for the back-end
  private Project project;

  // Model for the front-end
  private Project projectOld;
  private int projectID;
  private List<DeliverableType> deliverableTypes;
  private List<DeliverableType> deliverableSubTypes;
  private List<Integer> allYears;

  @Inject
  public ProjectDeliverablesListAction(APConfig config, DeliverableManager deliverableManager,
    DeliverableTypeManager deliverableTypeManager, ProjectManager projectManager) {
    super(config);
    this.deliverableManager = deliverableManager;
    this.deliverableTypeManager = deliverableTypeManager;
    this.projectManager = projectManager;
  }

  public List<Integer> getAllYears() {
    return allYears;
  }

  public List<DeliverableType> getDeliverableSubTypes() {
    return deliverableSubTypes;
  }

  public List<DeliverableType> getDeliverableTypes() {
    return deliverableTypes;
  }

  public Project getProject() {
    return project;
  }

  public int getProjectID() {
    return projectID;
  }

  public Project getProjectOld() {
    return projectOld;
  }


  @Override
  public String next() {
    String result = this.save();
    if (result.equals(BaseAction.SUCCESS)) {
      return BaseAction.NEXT;
    } else {
      return result;
    }
  }

  @Override
  public void prepare() throws Exception {

    projectID = Integer.parseInt(StringUtils.trim(this.getRequest().getParameter(APConstants.PROJECT_REQUEST_ID)));
    project = projectManager.getProject(projectID);

    // Getting the Deliverables Main Types.
    deliverableTypes = deliverableTypeManager.getDeliverableTypes();

    allYears = project.getAllYears();

    // Getting the List of Expected Deliverables

    List<Deliverable> deliverables = deliverableManager.getDeliverablesByProject(projectID);
    project.setDeliverables(deliverables);

  }

  @Override
  public String save() {
    return SUCCESS;
    // if (this.isSaveable()) {
    // boolean success = true;
    // boolean deleted;
    //
    // // Getting previous Deliverables.
    // List<Deliverable> previousDeliverables = deliverableManager.getDeliverablesByProject(projectID);
    //
    // // Identifying deleted deliverables in the interface to delete them from the database.
    // for (Deliverable deliverable : previousDeliverables) {
    // if (!project.getDeliverables().contains(deliverable) || project.getDeliverables().isEmpty()) {
    // deleted = deliverableManager.deleteDeliverable(deliverable.getId());
    // if (!deleted) {
    // success = false;
    // }
    // } else {
    // // If the deliverable was not deleted we should remove the next users and the outputs
    // nextUserManager.deleteNextUserByDeliverable(deliverable.getId());
    // deliverableManager.deleteDeliverableOutput(deliverable.getId());
    // }
    // }
    //
    // // Saving deliverables
    // for (Deliverable deliverable : project.getDeliverables()) {
    // int result = deliverableManager.saveDeliverable(projectID, deliverable);
    // if (result != -1) {
    // // Defining deliverable ID.
    // int deliverableID;
    // if (result > 0) {
    // deliverableID = result;
    // } else {
    // deliverableID = deliverable.getId();
    // }
    //
    // // saving output/MOG contribution.
    // deliverableManager.saveDeliverableOutput(deliverableID, project.getId(), this.getCurrentUser().getId(),
    // this.getJustification());
    //
    // // Saving next Users.
    // if (deliverable.getNextUsers() != null) {
    // for (NextUser nextUser : deliverable.getNextUsers()) {
    // if (!nextUserManager.saveNextUser(deliverableID, nextUser)) {
    // success = false;
    // }
    // }
    // }
    // }
    // }

    // if (success == false) {
    // this.addActionError(this.getText("saving.problem"));
    // return BaseAction.INPUT;
    // }
    // this.addActionMessage(this.getText("saving.success", new String[] {this.getText("planning.deliverables")}));
    // return BaseAction.SUCCESS;
    // } else {
    // return BaseAction.ERROR;
    // }
  }

  public void setDeliverableSubTypes(List<DeliverableType> deliverableSubTypes) {
    this.deliverableSubTypes = deliverableSubTypes;
  }

  public void setDeliverableTypes(List<DeliverableType> deliverableTypes) {
    this.deliverableTypes = deliverableTypes;
  }

  public void setProject(Project project) {
    this.project = project;
  }

  public void setProjectID(int projectID) {
    this.projectID = projectID;
  }

}