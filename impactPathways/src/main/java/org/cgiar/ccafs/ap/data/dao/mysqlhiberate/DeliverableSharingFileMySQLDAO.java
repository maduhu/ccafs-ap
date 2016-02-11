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


package org.cgiar.ccafs.ap.data.dao.mysqlhiberate;


import org.cgiar.ccafs.ap.data.dao.DeliverableDataSharingFileDAO;
import org.cgiar.ccafs.ap.data.model.DeliverableDataSharingFile;

import java.util.List;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeliverableSharingFileMySQLDAO implements DeliverableDataSharingFileDAO {

  private static Logger LOG = LoggerFactory.getLogger(DeliverableSharingFileMySQLDAO.class); // TODO To review

  private StandardDAO dao;

  @Inject
  public DeliverableSharingFileMySQLDAO(StandardDAO dao) {
    this.dao = dao;
  }

  @Override
  public boolean delete(DeliverableDataSharingFile dataSharingFile) {
    dao.delete(dataSharingFile);
    return true; // TODO To review
  }

  @Override
  public List<DeliverableDataSharingFile> findDeliverableDataSharingFile(int deliverableId) {
    String sql = "from " + DeliverableDataSharingFile.class.getName() + " where deliverable_id=" + deliverableId;
    List<DeliverableDataSharingFile> listDissemination = dao.findAll(sql);
    if (listDissemination.size() > 0) {
      return listDissemination;
    }
    return null;
  }


  @Override
  public int save(DeliverableDataSharingFile dataSharingFile) {
    dao.saveOrUpdate(dataSharingFile);
    return dataSharingFile.getId(); // TODO To review
  }
}