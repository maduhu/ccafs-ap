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

package org.cgiar.ccafs.ap.validation.planning;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.data.manager.IPElementManager;
import org.cgiar.ccafs.ap.data.model.IPElement;
import org.cgiar.ccafs.ap.data.model.OutputOverview;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.validation.BaseValidator;
import org.cgiar.ccafs.ap.validation.model.OutputOverviewValidator;
import org.cgiar.ccafs.ap.validation.model.ProjectValidator;

import com.google.inject.Inject;


/**
 * @author Hernán David Carvajal B. - CIAT/CCAFS
 * @author Héctor Fabio Tobón R. - CIAT/CCAFS
 */

public class ProjectOutputsPlanningValidator extends BaseValidator {

  // Validators
  private ProjectValidator projectValidator;
  private OutputOverviewValidator overviewValidator;

  // Managers
  private IPElementManager ipElementManager;

  @Inject
  public ProjectOutputsPlanningValidator(ProjectValidator projectValidator, OutputOverviewValidator overviewValidator,
    IPElementManager ipElementManager) {
    this.projectValidator = projectValidator;
    this.overviewValidator = overviewValidator;
    this.ipElementManager = ipElementManager;
  }

  public void validate(BaseAction action, Project project, String cycle) {
    if (project != null) {
      this.validateProjectJustification(action, project);

      if (project.isCoreProject() || project.isCoFundedProject()) {
        this.validateLessonsLearn(action, project, "outputs");
        this.validateOutputOverviews(action, project);
      } else {
        // If project is bilateral, do nothing.
      }

      if (validationMessage.length() > 0) {
        String msg = " " + action.getText("saving.missingFields", new String[] {validationMessage.toString()});
        action.addActionMessage(msg);
      }

      // Saving missing fields.
      this.saveMissingFields(project, cycle, "outputs");
    }
  }

  public void validateAnnualContribution(BaseAction action, Project project, OutputOverview overview, int counter) {
    StringBuilder msg = new StringBuilder();

    if (!overviewValidator.isValidExpectedAnnualContribution(overview.getExpectedAnnualContribution())) {
      IPElement output = project.getOutput(overview.getOutput().getId());
      int index = ipElementManager.getMOGIndex(output);
      msg.setLength(0);
      msg.append(action.getText("planning.projectOutputs.expectedBulletPoints.readText",
        new String[] {String.valueOf(overview.getYear())}).toLowerCase());
      msg.append("( ");
      msg.append(output.getProgram().getAcronym());
      msg.append(" - MOG #");
      msg.append(index);
      msg.append(") ");

      this.addMessage(msg.toString());
      this.addMissingField("project.outputsOverview[" + counter + "].expectedAnnualContribution");
    }
  }

  public void validateOutputOverviews(BaseAction action, Project project) {
    if (projectValidator.isValidOutputOverviews(project.getOutputsOverview())) {
      int c = 0;
      for (OutputOverview overview : project.getOutputsOverview()) {
        this.validateAnnualContribution(action, project, overview, c);
        this.validateSocialDimmension(action, project, overview, c);
        c++;
      }
    } else {
      action.addActionMessage(action.getText("planning.projectOutputs.validation.empty"));
    }
  }

  public void validateSocialDimmension(BaseAction action, Project project, OutputOverview overview, int counter) {
    StringBuilder msg = new StringBuilder();
    if (!overviewValidator.isValidExpectedAnnualContribution(overview.getSocialInclusionDimmension())) {
      IPElement output = project.getOutput(overview.getOutput().getId());
      int index = ipElementManager.getMOGIndex(output);
      msg.setLength(0);
      msg.append(action.getText("planning.projectOutputs.expectedSocialAndGenderPlan.readText",
        new String[] {String.valueOf(overview.getYear())}).toLowerCase());
      msg.append("( ");
      msg.append(output.getProgram().getAcronym());
      msg.append(" - MOG #");
      msg.append(index);
      msg.append(") ");
      this.addMessage(msg.toString());
      this.addMissingField("project.outputsOverview[" + counter + "].socialInclusionDimmension");
    }
  }
}
