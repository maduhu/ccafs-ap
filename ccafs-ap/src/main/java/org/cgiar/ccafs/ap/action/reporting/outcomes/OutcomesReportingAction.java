package org.cgiar.ccafs.ap.action.reporting.outcomes;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.data.manager.LogframeManager;
import org.cgiar.ccafs.ap.data.manager.OutcomeManager;
import org.cgiar.ccafs.ap.data.model.Outcome;

import java.util.List;

import com.google.inject.Inject;


public class OutcomesReportingAction extends BaseAction {

  private static final long serialVersionUID = -1903471529414936807L;

  // Managers
  private OutcomeManager outcomeManager;

  // Models
  private List<Outcome> outcomes;

  @Inject
  public OutcomesReportingAction(APConfig config, LogframeManager logframeManager, OutcomeManager outcomeManager) {
    super(config, logframeManager);
    this.outcomeManager = outcomeManager;
  }

  public List<Outcome> getOutcomes() {
    return outcomes;
  }

  @Override
  public void prepare() throws Exception {
    super.prepare();
    outcomes = outcomeManager.getOutcomes(this.getCurrentUser().getLeader(), this.getCurrentLogframe());

    // Remove all outcomes so they can be added again in the save method.
    if (this.getRequest().getMethod().equalsIgnoreCase("post")) {
      outcomes.clear();
    }
  }

  @Override
  public String save() {
    if (outcomes.size() > 0) {
      // Remove all outcomes from the database.
      boolean removed = outcomeManager.removeOutcomes(this.getCurrentUser().getLeader(), this.getCurrentLogframe());
      if (removed) {
        boolean added =
          outcomeManager.addOutcomes(outcomes, this.getCurrentUser().getLeader(), this.getCurrentLogframe());
        if (added) {
          addActionMessage(getText("saving.success", new String[] {getText("reporting.outcomes")}));
          return SUCCESS;
        }
      }
    }
    return INPUT;
  }

  public void setOutcomes(List<Outcome> outcomes) {
    this.outcomes = outcomes;
  }

  @Override
  public void validate() {
    super.validate();
    // Validate only when user click on save button
    if (getRequest().getMethod().equalsIgnoreCase("post")) {
      boolean problem = false;
      int c = 0;
      for (Outcome outcome : outcomes) {
        if (outcome.getOutcome().isEmpty()) {
          addFieldError("outcomes[" + c + "].outcome", getText("validation.field.required"));
          problem = true;
        }
        if (outcome.getOutputs().isEmpty()) {
          addFieldError("outcomes[" + c + "].outputs", getText("validation.field.required"));
          problem = true;
        }
        if (outcome.getPartners().isEmpty()) {
          addFieldError("outcomes[" + c + "].partners", getText("validation.field.required"));
          problem = true;
        }
        if (outcome.getOutputUser().isEmpty()) {
          addFieldError("outcomes[" + c + "].outputUser", getText("validation.field.required"));
          problem = true;
        }
        if (outcome.getHowUsed().isEmpty()) {
          addFieldError("outcomes[" + c + "].howUsed", getText("validation.field.required"));
          problem = true;
        }
        if (outcome.getEvidence().isEmpty()) {
          addFieldError("outcomes[" + c + "].evidence", getText("validation.field.required"));
          problem = true;
        }
        c++;
      }
      if (problem) {
        addActionError(getText("saving.problem"));
      }
    }
  }

}
