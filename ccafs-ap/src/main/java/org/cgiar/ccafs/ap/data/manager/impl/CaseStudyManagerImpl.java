package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.CaseStudyCountriesDAO;
import org.cgiar.ccafs.ap.data.dao.CaseStudyDAO;
import org.cgiar.ccafs.ap.data.dao.CaseStudyTypeDAO;
import org.cgiar.ccafs.ap.data.manager.CaseStudyManager;
import org.cgiar.ccafs.ap.data.model.CaseStudy;
import org.cgiar.ccafs.ap.data.model.Leader;
import org.cgiar.ccafs.ap.data.model.Logframe;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;


public class CaseStudyManagerImpl implements CaseStudyManager {

  private CaseStudyDAO caseStudyDAO;
  private CaseStudyCountriesDAO caseStudyCountriesDAO;
  private CaseStudyTypeDAO caseStudyTypeDAO;

  @Inject
  public CaseStudyManagerImpl(CaseStudyDAO caseStudyDAO, CaseStudyCountriesDAO caseStudyCountriesDAO,
    CaseStudyTypeDAO caseStudyTypeDAO) {
    this.caseStudyDAO = caseStudyDAO;
    this.caseStudyCountriesDAO = caseStudyCountriesDAO;
    this.caseStudyTypeDAO = caseStudyTypeDAO;
  }

  @Override
  public List<CaseStudy> getCaseStudyList(Leader leader, Logframe logframe) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    List<Map<String, String>> caseStudyDataList = caseStudyDAO.getCaseStudyList(leader.getId(), logframe.getId());
    Map<String, String> caseStudyData;
    List<CaseStudy> caseStudies = new ArrayList<>();

    for (int c = 0; c < caseStudyDataList.size(); c++) {
      caseStudyData = caseStudyDataList.get(c);

      CaseStudy temporalCaseStudy = new CaseStudy(Integer.parseInt(caseStudyData.get("id")));
      temporalCaseStudy.setTitle(caseStudyData.get("title"));
      temporalCaseStudy.setAuthor(caseStudyData.get("author"));
      try {
        // Parse from string to Date object
        temporalCaseStudy.setStartDate(dateFormat.parse(caseStudyData.get("start_date")));
        temporalCaseStudy.setEndDate(dateFormat.parse(caseStudyData.get("end_date")));
      } catch (ParseException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      temporalCaseStudy.setImageFileName(caseStudyData.get("photo"));
      temporalCaseStudy.setObjectives(caseStudyData.get("objectives"));
      temporalCaseStudy.setDescription(caseStudyData.get("description"));
      temporalCaseStudy.setResults(caseStudyData.get("results"));
      temporalCaseStudy.setPartners(caseStudyData.get("partners"));
      temporalCaseStudy.setLinks(caseStudyData.get("links"));
      temporalCaseStudy.setKeywords(caseStudyData.get("keywords"));
      temporalCaseStudy.setGlobal(Integer.parseInt(caseStudyData.get("is_global")) == 1);

      // Add the object to the list
      caseStudies.add(temporalCaseStudy);
    }

    return caseStudies;
  }

  @Override
  public boolean removeAllCaseStudies(int activityLeaderId, int logframeId) {
    boolean deleted = caseStudyDAO.removeAllCaseStudies(activityLeaderId, logframeId);
    return deleted;
  }

  @Override
  public boolean saveCaseStudy(CaseStudy caseStudy, int activityLeaderId, int logframeId) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    Map<String, Object> csData;
    csData = new HashMap<>();
    if (caseStudy.getId() != -1) {
      csData.put("id", caseStudy.getId());
    } else {
      csData.put("id", null);
    }
    csData.put("title", caseStudy.getTitle());
    csData.put("author", caseStudy.getAuthor());
    // Convert the date to string
    csData.put("start_date", sdf.format(caseStudy.getStartDate()));
    csData.put("end_date", sdf.format(caseStudy.getEndDate()));
    csData.put("photo", caseStudy.getImageFileName());
    csData.put("objectives", caseStudy.getObjectives());
    csData.put("description", caseStudy.getDescription());
    csData.put("results", caseStudy.getResults());
    csData.put("partners", caseStudy.getPartners());
    if (caseStudy.getLinks().isEmpty()) {
      csData.put("links", null);
    } else {
      csData.put("links", caseStudy.getLinks());
    }
    csData.put("keywords", caseStudy.getKeywords());
    csData.put("activity_leader_id", activityLeaderId);
    csData.put("logframe_id", logframeId);
    csData.put("is_global", caseStudy.isGlobal());

    int caseStudyId = caseStudyDAO.saveCaseStudy(csData);

    // If the case study id is not equal to -1, the database return zero as identifier
    // after the record insertion, so, set caseStudyId with its original value
    caseStudyId = (caseStudy.getId() != -1) ? caseStudy.getId() : caseStudyId;

    // if the case study was successfully saved, save the countries related and the case study types.
    if (caseStudyId >= 0) {
      // Save the countries
      if (!caseStudy.isGlobal()) {
        ArrayList<String> countriesIds = (ArrayList<String>) caseStudy.getCountriesIds();
        boolean caseStudyCountriesAdded = caseStudyCountriesDAO.saveCaseStudyCountries(caseStudyId, countriesIds);
        if (!caseStudyCountriesAdded) {
          return false;
        }
      }

      // Save the types
      ArrayList<String> typesIds = (ArrayList<String>) caseStudy.getTypesIds();
      int[] typesIdsArray = new int[typesIds.size()];
      for (int c = 0; c < typesIds.size(); c++) {
        typesIdsArray[c] = Integer.parseInt(typesIds.get(c));
      }
      boolean problemSavingTypes = caseStudyTypeDAO.saveCaseStudyTypes(caseStudyId, typesIdsArray);
      if (problemSavingTypes) {
        return false;
      }
    }

    return true;
  }
}
