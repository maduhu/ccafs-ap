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

package org.cgiar.ccafs.ap.action.summaries.planning.xls;

import org.cgiar.ccafs.ap.data.model.Institution;
import org.cgiar.ccafs.utils.APConfig;

import java.io.IOException;
import java.util.List;

import com.google.inject.Inject;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;


/**
 * @author Carlos Alberto Martínez M.
 */
public class PartnersSummaryXLS {

  private APConfig config;
  private BaseXLS xls;

  @Inject
  public PartnersSummaryXLS(APConfig config, BaseXLS xls) {
    this.config = config;
    this.xls = xls;
  }

  /**
   * This method is used to add an institution being a project partner
   *
   * @param projectPartnerInstitutions is the list of institutions to be added
   * @param projectList is the list with the projects related to each institution
   */
  private void addContent(Sheet sheet, List<Institution> projectPartnerInstitutions, String[] projectList) {
    int projectCount = 0;
    for (Institution institution : projectPartnerInstitutions) {
      xls.writeValue(sheet, institution.getId());
      xls.nextColumn();
      xls.writeValue(sheet, institution.getName());
      xls.nextColumn();
      xls.writeValue(sheet, institution.getAcronym());
      xls.nextColumn();
      xls.writeValue(sheet, institution.getWebsiteLink());
      xls.nextColumn();
      xls.writeValue(sheet, institution.getCountry().getName());
      xls.nextColumn();
      // Getting the project ids
      if (Integer.getInteger(projectList[projectCount]) instanceof Integer) {
        xls.writeValue(sheet, Integer.valueOf(projectList[projectCount]));
      } else {
        xls.writeValue(sheet, projectList[projectCount]);
      }
      projectCount++;
      xls.nextRow();
    }
  }

  /**
   * This method is used to generate the csv file for the ProjectPartner institutions.
   *
   * @param projectPartnerInstitutions is the list of institutions to be added
   * @param projectList is the list with the projects related to each institution
   * @return a byte array with the information provided for the xls file.
   */
  public byte[] generateCSV(List<Institution> projectPartnerInstitutions, String[] projectList) {

    try {
      Workbook workbook = xls.initializeXLS(true);
      String[] headers =
        new String[] {"Institution ID", "Institution name", "Institution acronym", "Web site", "Location", "Projects"};

      workbook.setSheetName(0, "ProjectPartnerInstitutions");
      Sheet sheet = workbook.getSheetAt(0);
      xls.writeTitleBox(sheet, "CCAFS Project Partner Institutions");
      xls.writeHeaders(sheet, headers);

      this.addContent(sheet, projectPartnerInstitutions, projectList);

      xls.writeWorkbook();
      byte[] byteArray = xls.getBytes();
      // Closing streams.
      xls.closeStreams();

      return byteArray;

    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }
}