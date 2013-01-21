package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.OutcomeDAO;
import org.cgiar.ccafs.ap.data.manager.OutcomeManager;
import org.cgiar.ccafs.ap.data.model.Leader;
import org.cgiar.ccafs.ap.data.model.Logframe;
import org.cgiar.ccafs.ap.data.model.Outcome;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;


public class OutcomeManagerImpl implements OutcomeManager {

  private OutcomeDAO outcomeDAO;

  @Inject
  public OutcomeManagerImpl(OutcomeDAO outcomeDAO) {
    this.outcomeDAO = outcomeDAO;
  }

  @Override
  public boolean addOutcomes(List<Outcome> newOutcomes, Leader leader, Logframe logframe) {
    List<Map<String, String>> outcomes = new ArrayList<>();
    Map<String, String> outcomeData;
    for (Outcome outcome : newOutcomes) {
      outcomeData = new HashMap<>();
      outcomeData.put("id", outcome.getId() + "");
      outcomeData.put("outcome", outcome.getOutcome());
      outcomeData.put("outputs", outcome.getOutputs());
      outcomeData.put("partners", outcome.getPartners());
      outcomeData.put("output_user", outcome.getOutputUser());
      outcomeData.put("how_used", outcome.getHowUsed());
      outcomeData.put("evidence", outcome.getEvidence());
      outcomeData.put("logframe_id", logframe.getId() + "");
      outcomeData.put("activity_leader_id", leader.getId() + "");
      outcomes.add(outcomeData);
    }
    return outcomeDAO.addOutcomes(outcomes);
  }

  @Override
  public List<Outcome> getOutcomes(Leader leader, Logframe logframe) {
    List<Outcome> outcomes = new ArrayList<>();
    List<Map<String, String>> outcomesData = outcomeDAO.getOutcomes(leader.getId(), logframe.getId());
    for (Map<String, String> outcomeData : outcomesData) {
      Outcome outcome = new Outcome();
      outcome.setId(Integer.parseInt(outcomeData.get("id")));
      outcome.setOutcome(outcomeData.get("outcome"));
      outcome.setOutputs(outcomeData.get("outputs"));
      outcome.setPartners(outcomeData.get("partners"));
      outcome.setOutputUser(outcomeData.get("output_user"));
      outcome.setHowUsed(outcomeData.get("how_used"));
      outcome.setEvidence(outcomeData.get("evidence"));
      outcome.setLogframe(logframe);
      outcome.setLeader(leader);
      outcomes.add(outcome);
    }
    return outcomes;
  }

  @Override
  public boolean removeOutcomes(Leader leader, Logframe logframe) {
    return outcomeDAO.removeOutcomes(leader.getId(), logframe.getId());
  }

}
