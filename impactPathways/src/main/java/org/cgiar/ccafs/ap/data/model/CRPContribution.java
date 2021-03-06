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

package org.cgiar.ccafs.ap.data.model;

import org.apache.commons.lang3.builder.ToStringBuilder;


/**
 * @author Javier Andres Gallego B. - CIAT/CCAFS
 */

public class CRPContribution {

  private CRP crp;
  private int id;
  private String natureCollaboration;
  private String explainAchieved;


  public CRPContribution() {

  }


  public CRPContribution(int id) {
    this.id = id;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof CRPContribution) {
      CRPContribution v = (CRPContribution) obj;
      return v.getId() == this.getId();
    }
    return false;
  }

  public CRP getCrp() {
    return crp;
  }

  public String getExplainAchieved() {
    return explainAchieved;
  }


  public int getId() {
    return id;
  }


  public String getNatureCollaboration() {
    return natureCollaboration;
  }


  @Override
  public int hashCode() {
    return this.id;
  }


  public void setCrp(CRP crp) {
    this.crp = crp;
  }

  public void setExplainAchieved(String explainAchieved) {
    this.explainAchieved = explainAchieved;
  }


  public void setId(int id) {
    this.id = id;
  }


  public void setNatureCollaboration(String natureCollaboration) {
    this.natureCollaboration = natureCollaboration;
  }


  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}