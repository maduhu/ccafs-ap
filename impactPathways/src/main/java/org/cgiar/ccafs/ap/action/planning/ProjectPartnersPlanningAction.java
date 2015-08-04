/*****************************************************************
 * This file is part of CCAFS Planning and Reporting Platform. CCAFS P&R is free software: you can redistribute it
 * and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation,
 * either version 3 of the License, or at your option) any later version. CCAFS P&R is distributed in the hope that it
 * will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU General Public License for more details. You should have received a copy of the GNU
 * General Public License along with CCAFS P&R. If not, see <http://www.gnu.org/licenses/>.
 *****************************************************************/
package org.cgiar.ccafs.ap.action.planning;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.BudgetManager;
import org.cgiar.ccafs.ap.data.manager.HistoryManager;
import org.cgiar.ccafs.ap.data.manager.InstitutionManager;
import org.cgiar.ccafs.ap.data.manager.LocationManager;
import org.cgiar.ccafs.ap.data.manager.ProjectManager;
import org.cgiar.ccafs.ap.data.manager.ProjectPartnerManager;
import org.cgiar.ccafs.ap.data.manager.UserManager;
import org.cgiar.ccafs.ap.data.model.Country;
import org.cgiar.ccafs.ap.data.model.Institution;
import org.cgiar.ccafs.ap.data.model.InstitutionType;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.ProjectPartner;
import org.cgiar.ccafs.ap.data.model.User;
import org.cgiar.ccafs.ap.validation.planning.ProjectPartnersValidator;
import org.cgiar.ccafs.utils.APConfig;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;
import com.opensymphony.xwork2.ActionContext;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is used to manage the Project Partners section in the planning step.
 * 
 * @author Hernán Carvajal
 * @author Héctor Fabio Tobón R. - CIAT/CCAFS
 * @author Carlos Alberto Martínez M
 */
public class ProjectPartnersPlanningAction extends BaseAction {

  private static final long serialVersionUID = 5839536146328620421L;
  public static Logger LOG = LoggerFactory.getLogger(ProjectPartnersPlanningAction.class);

  // Managers
  private ProjectPartnerManager projectPartnerManager;
  private InstitutionManager institutionManager;
  private LocationManager locationManager;
  private ProjectManager projectManager;
  private UserManager userManager;
  private BudgetManager budgetManager;
  private HistoryManager historyManager;
  private ProjectPartnersValidator projectPartnersValidator;

  // Model for the back-end
  private int projectID;
  private Project project;
  private Project previousProject;
  private String actionName;

  // Model for the view
  private List<InstitutionType> partnerTypes;
  private List<Country> countries;
  private List<Institution> allPartners; // Is used to list all the partners that have the system.
  private List<Institution> allPPAPartners; // Is used to list all the PPA partners
  private List<Institution> projectPPAPartners; // Is used to list all the PPA partners selected in the current project.
  private List<User> allProjectLeaders; // will be used to list all the project leaders that have the system.

  @Inject
  public ProjectPartnersPlanningAction(APConfig config, ProjectPartnerManager projectPartnerManager,
    InstitutionManager institutionManager, LocationManager locationManager, ProjectManager projectManager,
    UserManager userManager, BudgetManager budgetManager, HistoryManager historyManager,
    ProjectPartnersValidator projectPartnersValidator) {
    super(config);
    this.projectPartnerManager = projectPartnerManager;
    this.institutionManager = institutionManager;
    this.locationManager = locationManager;
    this.projectManager = projectManager;
    this.userManager = userManager;
    this.budgetManager = budgetManager;
    this.historyManager = historyManager;
    this.projectPartnersValidator = projectPartnersValidator;
  }

  public List<Institution> getAllPartners() {
    return allPartners;
  }

  public List<Institution> getAllPPAPartners() {
    return allPPAPartners;
  }

  public List<User> getAllProjectLeaders() {
    return allProjectLeaders;
  }

  public List<Country> getCountries() {
    return countries;
  }

  public List<InstitutionType> getPartnerTypes() {
    return partnerTypes;
  }

  public Project getProject() {
    return project;
  }

  public int getProjectID() {
    return projectID;
  }

  public List<Institution> getProjectPPAPartners() {
    return projectPPAPartners;
  }

  public String getProjectRequest() {
    return APConstants.PROJECT_REQUEST_ID;
  }

  public String getTypeProjectCoordinator() {
    return APConstants.PROJECT_PARTNER_PC;
  }

  public String getTypeProjectLeader() {
    return APConstants.PROJECT_PARTNER_PL;
  }

  public String getTypeProjectPartner() {
    return APConstants.PROJECT_PARTNER_PP;
  }

  public String getTypeProjectPPA() {
    return APConstants.PROJECT_PARTNER_PPA;
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
    super.prepare();
    actionName = ActionContext.getContext().getName();
    // Getting the project id from the URL parameter
    // It's assumed that the project parameter is ok. (@See ValidateProjectParameterInterceptor)
    projectID = Integer.parseInt(StringUtils.trim(this.getRequest().getParameter(APConstants.PROJECT_REQUEST_ID)));

    // Getting the project identified with the id parameter.
    project = projectManager.getProject(projectID);

    // Getting the list of all institutions
    allPartners = new ArrayList<>();
    allPartners.addAll(institutionManager.getAllInstitutions());

    // Getting the list of all PPA institutions
    allPPAPartners = new ArrayList<>();
    allPPAPartners.addAll(institutionManager.getAllPPAInstitutions());

    // Getting all the countries
    countries = locationManager.getInstitutionCountries();

    // Getting all partner types
    partnerTypes = institutionManager.getAllInstitutionTypes();

    // Getting all Project Leaders
    allProjectLeaders = userManager.getAllUsers();

    // Getting the Project Leader.
    List<ProjectPartner> ppArray =
      projectPartnerManager.getProjectPartners(project.getId(), APConstants.PROJECT_PARTNER_PL);
    if (ppArray.size() != 0) {
      project.setLeader(ppArray.get(0));
    }

    // Getting Project Coordinator
    ppArray = projectPartnerManager.getProjectPartners(project.getId(), APConstants.PROJECT_PARTNER_PC);
    if (ppArray.size() != 0) {
      project.setCoordinator(ppArray.get(0));
    }

    // Getting PPA Partners
    project.setPPAPartners(projectPartnerManager.getProjectPartners(project.getId(), APConstants.PROJECT_PARTNER_PPA));

    // Getting the list of PPA Partner institutions
    projectPPAPartners = new ArrayList<Institution>();
    for (ProjectPartner ppaPartner : project.getPPAPartners()) {
      projectPPAPartners.add(ppaPartner.getInstitution());
    }

    // Getting 2-level Project Partners
    project
    .setProjectPartners(projectPartnerManager.getProjectPartners(project.getId(), APConstants.PROJECT_PARTNER_PP));
    // Getting the 2-level Project Partner contributions
    for (ProjectPartner partner : project.getProjectPartners()) {
      partner.setContributeInstitutions(institutionManager.getProjectPartnerContributeInstitutions(partner));
    }

    // If the user is not admin or the project owner, we should keep some information
    // unmutable
    previousProject = new Project();
    previousProject.setId(project.getId());
    previousProject.setPPAPartners(project.getPPAPartners());

    if (actionName.equals("partnerLead")) {
      super.setHistory(historyManager.getProjectPartnersHistory(project.getId(), new String[] {
        APConstants.PROJECT_PARTNER_PL, APConstants.PROJECT_PARTNER_PC}));
    } else if (actionName.equals("ppaPartners")) {
      super.setHistory(historyManager.getProjectPartnersHistory(project.getId(),
        new String[] {APConstants.PROJECT_PARTNER_PPA}));
    } else if (actionName.equals("partners")) {
      super.setHistory(historyManager.getProjectPartnersHistory(project.getId(),
        new String[] {APConstants.PROJECT_PARTNER_PP}));
    }

    if (this.getRequest().getMethod().equalsIgnoreCase("post")) {
      // Clear out the list if it has some element
      if (project.getProjectPartners() != null) {
        project.getProjectPartners().clear();
      }
      if (project.getPPAPartners() != null) {
        project.getPPAPartners().clear();
      }
    }

  }

  @Override
  public String save() {
    switch (actionName) {
      case "partnerLead":
        if (securityContext.canUpdateProjectLeader()) {
          return this.savePartnerLead();
        } else {
          return NOT_AUTHORIZED;
        }

      case "ppaPartners":
        if (securityContext.canUpdateProjectPPAPartner()) {
          return this.savePartners(APConstants.PROJECT_PARTNER_PPA);
        } else {
          return NOT_AUTHORIZED;
        }

      case "partners":
        if (securityContext.canUpdateProjectPartners()) {
          return this.savePartners(APConstants.PROJECT_PARTNER_PP);
        } else {
          return NOT_AUTHORIZED;
        }
    }

    return NOT_AUTHORIZED;

  }

  private String savePartnerLead() {
    boolean success = true;

    // Saving Project leader
    int id =
      projectPartnerManager.saveProjectPartner(projectID, project.getLeader(), this.getCurrentUser(),
        this.getJustification());
    if (id < 0) {
      success = false;
    }

    // Saving Project Coordinator
    // Setting the same institution that was selected for the Project Leader.
    project.getCoordinator().setInstitution(project.getLeader().getInstitution());
    id =
      projectPartnerManager.saveProjectPartner(projectID, project.getCoordinator(), this.getCurrentUser(),
        this.getJustification());
    if (id < 0) {
      success = false;
    }

    if (success) {
      this.addActionMessage(this.getText("saving.saved"));
      return SUCCESS;
    }
    return INPUT;
  }

  private String savePartners(String partnerType) {
    boolean success = true;

    // Getting the partners coming from the view.
    List<ProjectPartner> partners;
    if (partnerType.equals(APConstants.PROJECT_PARTNER_PPA)) {
      partners = project.getPPAPartners();
    } else if (partnerType.equals(APConstants.PROJECT_PARTNER_PP)) {
      partners = project.getProjectPartners();
    } else {
      partners = new ArrayList<>();
    }

    // ----------------- PARTNERS ----------------------
    // Getting previous partners to identify those that need to be deleted.
    List<ProjectPartner> previousPartners = projectPartnerManager.getProjectPartners(projectID, partnerType);

    // Deleting project partners
    for (ProjectPartner previousPartner : previousPartners) {
      if (!partners.contains(previousPartner)) {
        boolean deleted =
          projectPartnerManager.deleteProjectPartner(previousPartner.getId(), this.getCurrentUser(),
            this.getJustification());
        if (!deleted) {
          success = false;
        }
      }
    }

    // Saving new and old PPA Partners
    boolean saved =
      projectPartnerManager.saveProjectPartners(projectID, partners, this.getCurrentUser(), this.getJustification());
    if (!saved) {
      saved = false;
    }

    // Saving project partner contributions
    if (partnerType.equals(APConstants.PROJECT_PARTNER_PP)) {
      // iterating each project partner
      for (ProjectPartner projectPartner : partners) {
        // Getting previous partner contributions to identify those that need to be deleted.
        List<Institution> previousPartnerContributions =
          institutionManager.getProjectPartnerContributeInstitutions(projectPartner);
        // Deleting project partner contributions
        for (Institution previousPartnerContribution : previousPartnerContributions) {
          if (projectPartner.getContributeInstitutions() == null
            || !projectPartner.getContributeInstitutions().contains(previousPartnerContribution)) {
            boolean deleted =
              institutionManager.deleteProjectPartnerContributeInstitution(projectPartner.getId(),
                previousPartnerContribution.getId());
            if (!deleted) {
              success = false;
            }
          }
        }

        // if the project partner has contribute institutions.
        if (projectPartner.getContributeInstitutions() != null) {
          // Saving new and old Project Partner Contributions
          saved =
            institutionManager.saveProjectPartnerContributeInstitutions(projectPartner.getId(),
              projectPartner.getContributeInstitutions());
          if (!saved) {
            saved = false;
          }
        }
      } // End loop
    }

    if (success) {
      this.addActionMessage(this.getText("saving.saved"));
      return SUCCESS;
    }
    return INPUT;
  }

  public void setAllProjectLeaders(List<User> allProjectLeaders) {
    this.allProjectLeaders = allProjectLeaders;
  }

  public void setProject(Project project) {
    this.project = project;
  }

  public void setProjectID(int projectID) {
    this.projectID = projectID;
  }

  @Override
  public void validate() {
    LOG.debug(">> validate() ");
    // validate only if user clicks any save button.
    if (save) {
      projectPartnersValidator.validate(this, project);
    }
  }

}
