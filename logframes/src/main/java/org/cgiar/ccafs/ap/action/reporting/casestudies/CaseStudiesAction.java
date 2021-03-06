package org.cgiar.ccafs.ap.action.reporting.casestudies;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.CaseStudyCountriesManager;
import org.cgiar.ccafs.ap.data.manager.CaseStudyManager;
import org.cgiar.ccafs.ap.data.manager.CaseStudyTypeManager;
import org.cgiar.ccafs.ap.data.manager.CountryManager;
import org.cgiar.ccafs.ap.data.manager.LogframeManager;
import org.cgiar.ccafs.ap.data.manager.SubmissionManager;
import org.cgiar.ccafs.ap.data.model.CaseStudy;
import org.cgiar.ccafs.ap.data.model.CaseStudyType;
import org.cgiar.ccafs.ap.data.model.Country;
import org.cgiar.ccafs.ap.data.model.Submission;
import org.cgiar.ccafs.ap.util.Capitalize;
import org.cgiar.ccafs.ap.util.FileManager;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CaseStudiesAction extends BaseAction {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(CaseStudiesAction.class);
  private static final long serialVersionUID = -6393409409918755065L;

  // Managers
  private CaseStudyManager caseStudyManager;
  private CaseStudyCountriesManager caseStudyCountriesManager;
  private CaseStudyTypeManager caseStudyTypeManager;
  private CountryManager countryManager;
  private SubmissionManager submissionManager;

  // Model
  private List<CaseStudy> caseStudies;
  private Country[] countryList;
  private Map<Integer, String> imageNameMap;
  private CaseStudyType[] caseStudyTypeList;
  private StringBuilder validationMessage;
  private boolean canSubmit;


  @Inject
  public CaseStudiesAction(APConfig config, LogframeManager logframeManager, CaseStudyManager caseStudyManager,
    CaseStudyTypeManager caseStudyTypeManager, CountryManager countryManager,
    CaseStudyCountriesManager caseStudyCountriesManager, SubmissionManager submissionManager) {
    super(config, logframeManager);
    this.caseStudyManager = caseStudyManager;
    this.countryManager = countryManager;
    this.caseStudyTypeManager = caseStudyTypeManager;
    this.caseStudyCountriesManager = caseStudyCountriesManager;
    this.submissionManager = submissionManager;

    validationMessage = new StringBuilder();
  }

  /**
   * Delete all the images stored in the hard disk
   * which are not being used
   */
  private void deleteUnusedImages() {
    LOG.info("The user {} is going to erase the unused images of case studies", getCurrentUser().getEmail());
    Collection<String> imageNames = imageNameMap.values();
    // Remove from the list of names all the images used
    // by a case study
    for (CaseStudy caseStudy : caseStudies) {
      if (imageNames.contains(caseStudy.getImageFileName())) {
        imageNames.remove(caseStudy.getImageFileName());
      }
    }
    // All the elements that still in the list
    // aren't used, then, delete them from the disk
    for (String imageName : imageNames) {
      LOG.info("Deleting image {} from the disk", imageName);
      FileManager.deleteFile(getFolderPath(imageName));
    }
  }

  public List<CaseStudy> getCaseStudies() {
    return caseStudies;
  }

  /**
   * Join the url to the folder of cases studies images and
   * the organization folders to return the complete url where
   * are located the images
   * 
   * @return the url where images are
   */
  public String getCaseStudiesImagesUrl() {
    return config.getCaseStudiesImagesUrl() + "/" + getCurrentReportingLogframe().getYear() + "/"
      + getCurrentUser().getLeader().getAcronym() + "/";
  }


  public CaseStudyType[] getCaseStudyTypeList() {
    return caseStudyTypeList;
  }

  public Country[] getCountryList() {
    return countryList;
  }

  public int getCurrentYear() {
    return config.getReportingCurrentYear();
  }

  public int getEndYear() {
    return config.getEndYear();
  }

  /**
   * Join the path to the folder of cases studies images, the organization
   * folders path and the image name to return the complete path
   * where is or will be stored the image
   * 
   * @param imageName
   * @return complete path where the image is stored
   */
  private String getFolderPath(String imageName) {
    return config.getCaseStudiesImagesPath() + File.separator + getCurrentReportingLogframe().getYear()
      + File.separator + getCurrentUser().getLeader().getAcronym() + File.separator + imageName;
  }

  /**
   * This function returns the number maximum of case study types
   * that can have a case study
   */
  public int getMaxCaseStudyTypes() {
    return config.getMaxCaseStudyTypes();
  }

  public boolean isCanSubmit() {
    return canSubmit;
  }

  @Override
  public String next() {
    save();
    return super.next();
  }

  @Override
  public void prepare() throws Exception {
    super.prepare();
    LOG.info("The user {} load the case study section", getCurrentUser().getEmail());
    caseStudies = caseStudyManager.getCaseStudyList(getCurrentUser().getLeader(), getCurrentReportingLogframe());
    countryList = countryManager.getCountryList();
    caseStudyTypeList = caseStudyTypeManager.getCaseStudyTypes();

    // Initialize the map of image's names
    imageNameMap = new HashMap<>();

    // If there are elements in the case study list, iterate it to store
    // the corresponding list of countries and the list of types
    List<Country> temporalCountryList;
    List<CaseStudyType> temporalTypeList;
    for (int c = 0; c < caseStudies.size(); c++) {
      temporalCountryList = caseStudyCountriesManager.getCaseStudyCountriesList(caseStudies.get(c));
      caseStudies.get(c).setCountries(temporalCountryList);

      temporalTypeList = caseStudyTypeManager.getCaseStudyTypes(caseStudies.get(c));
      caseStudies.get(c).setTypes(temporalTypeList);

      // If the case study has an image name, store it into the image names map
      // Key -> caseStudy.id ,Value -> image name
      if (caseStudies.get(c).getImageFileName() != null) {
        imageNameMap.put(caseStudies.get(c).getId(), caseStudies.get(c).getImageFileName());
      }
    }

    // Remove all caseStudies in case user clicked on submit button
    if (this.getRequest().getMethod().equalsIgnoreCase("post")) {
      caseStudies.clear();
      LOG.debug("All the case studies related to the leader {} was deleted", getCurrentUser().getLeader().getId());
    }

    /* --------- Checking if the user can submit ------------- */
    Submission submission =
      submissionManager.getSubmission(getCurrentUser().getLeader(), getCurrentReportingLogframe(),
        APConstants.REPORTING_SECTION);

    canSubmit = (submission == null) ? true : false;
  }

  @Override
  public String save() {
    boolean problem = false;

    // First, remove all the case studies from the database
    boolean deleted =
      caseStudyManager
        .removeAllCaseStudies(getCurrentUser().getLeader().getId(), getCurrentReportingLogframe().getId());
    if (!deleted) {
      problem = true;
    } else {
      for (int c = 0; c < caseStudies.size(); c++) {
        // Second, add the list of case studies into the database

        // If the user don't upload an image
        if (caseStudies.get(c).getImage() == null) {
          // Check if there is an image name related to the case study in
          // image names map
          if (imageNameMap.get(caseStudies.get(c).getId()) != null) {
            // If there is one, assign it to the case study
            caseStudies.get(c).setImageFileName(imageNameMap.get(caseStudies.get(c).getId()));
          }
        } else {
          caseStudies.get(c).setImageFileName(caseStudies.get(c).getImageFileName());
        }

        boolean added =
          caseStudyManager.saveCaseStudy(caseStudies.get(c), getCurrentUser().getLeader().getId(),
            getCurrentReportingLogframe().getId());

        if (!added) {
          problem = true;
        } else {
          // If there was not problem saving into the DAO and
          // the user uploads an image, move the image from temporal folder to
          // the appropriate folder

          if (caseStudies.get(c).getImage() != null) {
            // If the user uploads an image, check if the name is the same name stored
            // in the image names map to this case study
            if (!caseStudies.get(c).getImageFileName().equals(imageNameMap.get(caseStudies.get(c).getId()))) {
              // If they aren't the same, delete the previous file
              FileManager.deleteFile(getFolderPath(imageNameMap.get(caseStudies.get(c).getId())));
              // next, copy the new image from temporal folder to its final folder
              FileManager.copyFile(caseStudies.get(c).getImage(), getFolderPath(caseStudies.get(c).getImageFileName()));
            }
          }
        }
      }
    }

    // Finally, delete from the disk the unused images
    deleteUnusedImages();

    if (!problem) {
      if (validationMessage.toString().isEmpty()) {
        addActionMessage(getText("saving.success", new String[] {getText("reporting.caseStudies")}));
      } else {
        String finalMessage = getText("saving.success", new String[] {getText("reporting.caseStudies")});
        finalMessage += getText("saving.keepInMind", new String[] {validationMessage.toString()});
        addActionWarning(Capitalize.capitalizeString(finalMessage));
      }
      return SUCCESS;
    } else {
      addActionError(getText("saving.problem"));
      return INPUT;
    }
  }

  public void setCaseStudies(List<CaseStudy> caseStudies) {
    this.caseStudies = caseStudies;
  }

  @Override
  public void validate() {
    boolean anyError = false;
    boolean validationError = false;

    // Validations
    if (save) {
      for (int c = 0; c < caseStudies.size(); c++) {
        // Title
        if (caseStudies.get(c).getTitle().isEmpty()) {
          validationMessage.append(getText("reporting.caseStudies.validation.title") + ", ");
          validationError = true;
        }
        // Author
        if (caseStudies.get(c).getAuthor().isEmpty()) {
        }
        // Type
        // If a new case study don't select a type the attribute is null
        if (caseStudies.get(c).getTypes() == null) {
          validationMessage.append(getText("reporting.caseStudies.validation.type") + ", ");
          validationError = true;
        } else if (caseStudies.get(c).getTypes().size() == 0) {
          validationMessage.append(getText("reporting.caseStudies.validation.type") + ", ");
          validationError = true;
        }
        // Photo
        if (caseStudies.get(c).getImage() != null) {
          String type = caseStudies.get(c).getImageContentType().split("/")[0];

          // Check if the file type is an image
          if (!type.equalsIgnoreCase("image")) {
            addFieldError("caseStudies[" + c + "].image",
              getText("validation.file.badFormat", new String[] {"an image"}));
            anyError = true;
          }

          // Check the file size
          if (caseStudies.get(c).getImage().length() > config.getFileMaxSize()) {
            addFieldError("caseStudies[" + c + "].image",
              getText("validation.file.tooLarge", new String[] {config.getFileMaxSize() + ""}));
            anyError = true;
          }
        }

        // Start date, if the user don't enter a value, the object is null
        if (caseStudies.get(c).getStartDate() == null) {
          validationMessage.append(getText("reporting.caseStudies.validation.startDate") + ", ");
          validationError = true;
        }
        // End date, if the user don't enter a value, the object is null
        if (caseStudies.get(c).getEndDate() == null) {
          validationMessage.append(getText("reporting.caseStudies.validation.endDate") + ", ");
          validationError = true;
        }
        // Countries
        // If the case study is not global check if there are countries
        if (!caseStudies.get(c).isGlobal()) {
          if (caseStudies.get(c).getCountries() == null) {
            validationMessage.append(getText("reporting.caseStudies.validation.location") + ", ");
            validationError = true;
          } else if (caseStudies.get(c).getCountries().size() == 0) {
            validationMessage.append(getText("reporting.caseStudies.validation.location") + ", ");
            validationError = true;
          }
        }
        // Keywords
        if (caseStudies.get(c).getKeywords().isEmpty()) {
          validationMessage.append(getText("reporting.caseStudies.validation.keywords") + ", ");
          validationError = true;
        }
        // Objectives
        if (caseStudies.get(c).getObjectives().isEmpty()) {
          validationMessage.append(getText("reporting.caseStudies.validation.objectives") + ", ");
          validationError = true;
        }
        // Description
        if (caseStudies.get(c).getDescription().isEmpty()) {
          validationMessage.append(getText("reporting.caseStudies.validation.description") + ", ");
          validationError = true;
        }
        // Results
        if (caseStudies.get(c).getResults().isEmpty()) {
          validationMessage.append(getText("reporting.caseStudies.validation.results") + ", ");
          validationError = true;
        }
        // Partners
        if (caseStudies.get(c).getPartners().isEmpty()) {
          validationMessage.append(getText("reporting.caseStudies.validation.partners") + ", ");
          validationError = true;
        }
        // Type
        if (caseStudies.get(c).getTypes().size() > config.getMaxCaseStudyTypes()) {
          validationMessage.append(getText("reporting.caseStudies.validation.types") + ", ");
          validationError = true;
        }
      }
    }

    if (anyError) {
      addActionError(getText("saving.fields.required"));
    }

    // Change the last colon by a period
    if (validationError == true) {
      validationMessage.setCharAt(validationMessage.lastIndexOf(","), '.');
    }
  }
}