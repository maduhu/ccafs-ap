package org.cgiar.ccafs.ap.data.dao.mysql;

import org.cgiar.ccafs.ap.data.dao.DAOManager;
import org.cgiar.ccafs.ap.data.dao.InstitutionDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MySQLInstitutionDAO implements InstitutionDAO {

  public static Logger LOG = LoggerFactory.getLogger(MySQLInstitutionDAO.class);
  private DAOManager databaseManager;

  @Inject
  public MySQLInstitutionDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  @Override
  public List<Map<String, String>> getInstitutionsByUser(int userID) {
    List<Map<String, String>> institutionsDataList = new ArrayList<>();
    StringBuilder query = new StringBuilder();
    query.append("SELECT i.id, i.name, i.acronym, ");
    query.append("p.id as program_id, p.name as program_name, p.acronym as 'program_acronym', ");
    query.append("it.id as institution_type_id, it.acronym as 'institution_type_acronym' ");
    query.append("FROM institutions i ");
    query.append("LEFT JOIN ip_programs p ON i.program_id = p.id ");
    query.append("LEFT JOIN institution_types it ON i.institution_type_id = it.id ");

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      while (rs.next()) {
        Map<String, String> institutionData = new HashMap<String, String>();
        institutionData.put("id", rs.getString("id"));
        institutionData.put("name", rs.getString("name"));
        institutionData.put("acronym", rs.getString("acronym"));
        institutionData.put("program_id", rs.getString("program_id"));
        institutionData.put("program_name", rs.getString("program_name"));
        institutionData.put("program_acronym", rs.getString("program_acronym"));
        institutionData.put("institution_type_id", rs.getString("institution_type_id"));
        institutionData.put("institution_type_acronym", rs.getString("institution_type_acronym"));

        institutionsDataList.add(institutionData);
      }
      con.close();
    } catch (SQLException e) {
      LOG.error("Exception arised getting the institutions for the user {}.", userID, e);
    }

    return institutionsDataList;
  }

  @Override
  public Map<String, String> getUserMainInstitution(int userID) {
    Map<String, String> institutionData = new HashMap<>();
    StringBuilder query = new StringBuilder();
    query.append("SELECT i.id, i.name, i.acronym, ");
    query.append("p.id as program_id, p.name as program_name, p.acronym as 'program_acronym', ");
    query.append("it.id as institution_type_id, it.acronym as 'institution_type_acronym' ");
    query.append("FROM institutions i ");
    query.append("LEFT JOIN ip_programs p ON i.program_id = p.id ");
    query.append("LEFT JOIN institution_types it ON i.institution_type_id = it.id ");
    query.append("INNER JOIN employees e ON i.id = e.institution_id AND is_main = TRUE ");
    query.append("INNER JOIN users u ON e.user_id = u.id AND user_id = ");
    query.append(userID);

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      if (rs.next()) {
        institutionData.put("id", rs.getString("id"));
        institutionData.put("name", rs.getString("name"));
        institutionData.put("acronym", rs.getString("acronym"));
        institutionData.put("program_id", rs.getString("program_id"));
        institutionData.put("program_name", rs.getString("program_name"));
        institutionData.put("program_acronym", rs.getString("program_acronym"));
        institutionData.put("institution_type_id", rs.getString("institution_type_id"));
        institutionData.put("institution_type_acronym", rs.getString("institution_type_acronym"));

      }
      con.close();
    } catch (SQLException e) {
      LOG.error("Exception arised getting the institutions for the user {}.", userID, e);
    }

    return institutionData;
  }

}
