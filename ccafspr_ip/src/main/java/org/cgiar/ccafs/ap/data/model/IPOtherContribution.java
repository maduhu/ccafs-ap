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
 * This class represents Other Contributions for a specific activity.
 * 
 * @author Javier Andrés Gallego B.
 */
public class IPOtherContribution {

  private int id;
  private String contribution;
  private String additionalContribution;


  public IPOtherContribution() {

  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof IPOtherContribution) {
      IPOtherContribution n = (IPOtherContribution) obj;
      return n.id == this.id;
    }
    return false;
  }


  public String getAdditionalContribution() {
    return additionalContribution;
  }


  public String getContribution() {
    return contribution;
  }


  public int getId() {
    return id;
  }


  @Override
  public int hashCode() {
    return this.id;
  }


  public void setAdditionalContribution(String additionalContribution) {
    this.additionalContribution = additionalContribution;
  }


  public void setContribution(String contribution) {
    this.contribution = contribution;
  }


  public void setId(int id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}
