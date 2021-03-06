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

package org.cgiar.ccafs.ap.validation.model;

import org.cgiar.ccafs.ap.validation.BaseValidator;

import com.google.inject.Inject;


/**
 * @author Hernán David Carvajal B. - CIAT/CCAFS
 * @author Héctor Fabio Tobón R. - CIAT/CCAFS
 */

public class OutputOverviewValidator extends BaseValidator {

  @Inject
  public OutputOverviewValidator() {
  }

  public boolean isValidBriefSummary(String briefSummary) {
    return (this.isValidString(briefSummary) && this.wordCount(briefSummary) <= 50) ? true : false;
  }

  public boolean isValidExpectedAnnualContribution(String annualContribution) {
    return (this.isValidString(annualContribution) && this.wordCount(annualContribution) <= 50) ? true : false;
  }

  public boolean isValidGenderSummary(String genderSummary) {
    return (this.isValidString(genderSummary) && this.wordCount(genderSummary) <= 50) ? true : false;
  }

  public boolean isValidSocialInclusionDimmension(String socialDimmension) {
    return (this.isValidString(socialDimmension) && this.wordCount(socialDimmension) <= 50) ? true : false;
  }

}
