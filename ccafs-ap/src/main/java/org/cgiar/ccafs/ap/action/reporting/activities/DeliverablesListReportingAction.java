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

package org.cgiar.ccafs.ap.action.reporting.activities;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.ActivityManager;
import org.cgiar.ccafs.ap.data.manager.DeliverableManager;
import org.cgiar.ccafs.ap.data.manager.LogframeManager;
import org.cgiar.ccafs.ap.data.manager.SubmissionManager;
import org.cgiar.ccafs.ap.data.model.Activity;
import org.cgiar.ccafs.ap.data.model.Submission;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Hernán David Carvajal
 */

public class DeliverablesListReportingAction extends BaseAction {

  // Logger
  public static Logger LOG = LoggerFactory.getLogger(DeliverablesListReportingAction.class);

  // Managers
  private DeliverableManager deliverableManager;
  private ActivityManager activityManager;
  private SubmissionManager submissionManager;

  // Model
  private Activity activity;
  private int activityID;
  private boolean canSubmit;

  @Inject
  public DeliverablesListReportingAction(APConfig config, LogframeManager logframeManager,
    DeliverableManager deliverableManager, ActivityManager activityManager, SubmissionManager submissionManager) {
    super(config, logframeManager);
    this.deliverableManager = deliverableManager;
    this.activityManager = activityManager;
    this.submissionManager = submissionManager;
  }

  public Activity getActivity() {
    return activity;
  }

  public int getActivityID() {
    return activityID;
  }

  public String getActivityRequestParameter() {
    return APConstants.ACTIVITY_REQUEST_ID;
  }

  public String getDeliverableRequestParameter() {
    return APConstants.DELIVERABLE_REQUEST_ID;
  }

  public boolean isCanSubmit() {
    return canSubmit;
  }

  @Override
  public void prepare() throws Exception {
    activityID = Integer.parseInt(StringUtils.trim(this.getRequest().getParameter(APConstants.ACTIVITY_REQUEST_ID)));
    activity = activityManager.getSimpleActivity(activityID);

    LOG.info("There user {} is loading the list of deliverables for the activity {}", getCurrentUser().getEmail(),
      String.valueOf(activityID));

    activity.setDeliverables(deliverableManager.getDeliverableByActivityID(activityID));

    /* --------- Checking if the user can submit ------------- */
    Submission submission =
      submissionManager.getSubmission(getCurrentUser().getLeader(), getCurrentReportingLogframe(),
        APConstants.REPORTING_SECTION);
    canSubmit = (submission == null) ? true : false;
  }

  public void setActivity(Activity activity) {
    this.activity = activity;
  }

  public void setActivityID(int activityID) {
    this.activityID = activityID;
  }
}