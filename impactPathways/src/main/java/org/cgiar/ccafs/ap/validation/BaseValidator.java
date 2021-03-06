package org.cgiar.ccafs.ap.validation;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.SectionStatusManager;
import org.cgiar.ccafs.ap.data.model.ComponentLesson;
import org.cgiar.ccafs.ap.data.model.Deliverable;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.SectionStatus;
import org.cgiar.ccafs.utils.APConfig;

import javax.mail.internet.InternetAddress;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class BaseValidator {

  private static final Logger LOG = LoggerFactory.getLogger(BaseValidator.class);

  @Inject
  protected APConfig config;
  protected StringBuilder validationMessage;
  protected StringBuilder missingFields;

  // Managers
  @Inject
  private SectionStatusManager statusManager;

  @Inject
  public BaseValidator() {
    validationMessage = new StringBuilder();
    missingFields = new StringBuilder();
  }

  protected void addMessage(String message) {
    validationMessage.append("<p> - ");
    validationMessage.append(message);
    validationMessage.append("</p>");
  }

  /**
   * This method add a missing field separated by a semicolon (;).
   * 
   * @param field is the name of the field.
   */
  protected void addMissingField(String field) {
    if (missingFields.length() != 0) {
      missingFields.append(";");
    }
    missingFields.append(field);
  }

  protected boolean isValidEmail(String email) {
    if (email != null) {
      try {
        InternetAddress internetAddress = new InternetAddress(email);
        internetAddress.validate();
        return true;
      } catch (javax.mail.internet.AddressException e) {
        email = (email == null) ? "" : email;
        LOG.debug("Email address was invalid: " + email);
      }
    }
    return false;
  }

  protected boolean isValidNumber(String number) {
    if (this.isValidString(number)) {
      try {
        Double.parseDouble(number);
        // If is a number the code comes to here.
        return true;
      } catch (NumberFormatException e) {
        // if not a number.
        return false;
      }
    }
    return false;
  }

  /**
   * This method validates that the string received is not null and is not empty.
   * 
   * @param string
   * @return true if the string is valid. False otherwise.
   */
  protected boolean isValidString(String string) {
    if (string != null) {
      return !string.trim().isEmpty();
    }
    return false;
  }

  /**
   * This method saves the missing fields into the database for a section at deliverable level.
   * 
   * @param project is a project.
   * @param deliveralbe is a deliverable
   * @param cycle could be 'Planning' or 'Reporting'
   * @param sectionName is the name of the section inside deliverables.
   */
  protected void saveMissingFields(Project project, Deliverable deliverable, String cycle, String sectionName) {
    // Reporting missing fields into the database.
    int year = 0;
    if (cycle.equals(APConstants.REPORTING_SECTION)) {
      year = config.getReportingCurrentYear();
    } else {
      year = config.getPlanningCurrentYear();
    }
    SectionStatus status = statusManager.getSectionStatus(deliverable, cycle, sectionName, year);
    if (status == null) {

      status = new SectionStatus(cycle, sectionName, year);
    }
    status.setMissingFields(this.missingFields.toString());
    statusManager.saveSectionStatus(status, project, deliverable);
  }

  /**
   * This method saves the missing fields into the database for a section at project level.
   * 
   * @param project is a project.
   * @param cycle could be 'Planning' or 'Reporting'
   * @param sectionName is the name of the section (description, partners, etc.).
   */
  protected void saveMissingFields(Project project, String cycle, String sectionName) {
    // Reporting missing fields into the database.
    int year = 0;
    if (cycle.equals(APConstants.REPORTING_SECTION)) {
      year = config.getReportingCurrentYear();
    } else {
      year = config.getPlanningCurrentYear();
    }
    SectionStatus status = statusManager.getSectionStatus(project, cycle, sectionName, year);
    if (status == null) {

      status = new SectionStatus(cycle, sectionName, year);
    }
    status.setMissingFields(this.missingFields.toString());
    statusManager.saveSectionStatus(status, project);
  }

  protected void validateLessonsLearn(BaseAction action, Project project, String section) {
    if (!project.isNew(config.getCurrentPlanningStartDate())) {
      ComponentLesson lesson = action.getProjectLessons();
      if (!(this.isValidString(lesson.getLessons()) && this.wordCount(lesson.getLessons()) <= 100)) {
        // Let them save.
        this.addMessage("Lessons");
        // action.addFieldError("projectLessons.lessons", action.getText("validation.field.required"));
        this.addMissingField("projectLessons.lessons");
      }
    }
  }


  protected void validateLessonsLearnSynthesis(BaseAction action) {

    ComponentLesson lesson = action.getProjectLessons();
    if (!(this.isValidString(lesson.getLessons()) && this.wordCount(lesson.getLessons()) <= 100)) {
      // Let them save.
      this.addMessage("Lessons");
      // action.addFieldError("projectLessons.lessons", action.getText("validation.field.required"));


    }
  }

  /**
   * This method verify if the project was created in the current planning phase, if it was created previously the user
   * should provide a justification of the changes.
   * 
   * @param project
   */
  protected void validateProjectJustification(BaseAction action, Deliverable deliverable) {
    if (!deliverable.isNew(config.getPlanningCurrentYear())) {
      if (action.getJustification() == null || action.getJustification().isEmpty()) {
        action.addActionError(action.getText("validation.justification"));
        action.addFieldError("justification", action.getText("validation.field.required"));
      }
    }
  }

  /**
   * This method verify if the project was created in the current planning phase, if it was created previously the user
   * should provide a justification of the changes.
   * 
   * @param project
   */
  protected void validateProjectJustification(BaseAction action, Project project) {
    if (!project.isNew(config.getCurrentPlanningStartDate())) {
      if (action.getJustification() == null || action.getJustification().isEmpty()) {
        action.addActionError(action.getText("validation.justification"));
        action.addFieldError("justification", action.getText("validation.field.required"));
      }
    }
  }

  /**
   * This method counts the number of words in a given text.
   * 
   * @param text is some text to be validated.
   * @return the number of words.
   */
  protected int wordCount(String text) {
    text = text.trim();
    return text.isEmpty() ? 0 : text.split("\\s+").length;
  }
}
