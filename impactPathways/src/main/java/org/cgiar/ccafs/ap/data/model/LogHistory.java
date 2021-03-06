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

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;


/**
 * @author Hernán David Carvajal B. - CIAT/CCAFS
 */


public class LogHistory<T> {

  private User user;
  private T objectHistory;
  private String justification;
  private String action;
  private Date date;

  public String getAction() {
    return action;
  }

  public Date getDate() {
    return date;
  }

  public String getJustification() {
    return justification;
  }

  public T getObjectHistory() {
    return objectHistory;
  }

  public User getUser() {
    return user;
  }

  public void setAction(String action) {
    this.action = action;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public void setJustification(String justification) {
    this.justification = justification;
  }

  public void setObjectHistory(T objectHistory) {
    this.objectHistory = objectHistory;
  }

  public void setUser(User user) {
    this.user = user;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}