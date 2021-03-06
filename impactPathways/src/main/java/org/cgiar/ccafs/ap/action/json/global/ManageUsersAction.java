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

package org.cgiar.ccafs.ap.action.json.global;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.UserManager;
import org.cgiar.ccafs.ap.data.model.User;
import org.cgiar.ccafs.utils.APConfig;
import org.cgiar.ccafs.utils.SendMail;

import org.cgiar.ciat.auth.LDAPService;
import org.cgiar.ciat.auth.LDAPUser;

import java.util.List;

import com.google.inject.Inject;
import com.opensymphony.xwork2.ActionContext;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Hernán David Carvajal
 * @author Héctor F. Tobón R. - CIAT/CCAFS
 */

public class ManageUsersAction extends BaseAction {

  private static final long serialVersionUID = 281018603716118132L;
  private static Logger LOG = LoggerFactory.getLogger(ManageUsersAction.class);

  private static String PARAM_FIRST_NAME = "firstName";
  private static String PARAM_LAST_NAME = "lastName";
  private static String PARAM_EMAIL = "email";
  private static String PARAM_IS_ACTIVE = "isActive";

  private UserManager userManager;
  private SendMail sendMail;

  private String queryParameter;
  private List<User> users;
  private User newUser;
  private String message;

  @Inject
  public ManageUsersAction(APConfig config, UserManager userManager, SendMail sendMail) {
    super(config);
    this.userManager = userManager;
    this.sendMail = sendMail;
  }

  /**
   * Add a new user into the database;
   * 
   * @return true if the user was successfully added, false otherwise.
   */
  private boolean addUser() {
    // int id = userManager.saveUser(newUser, userManager.getUser(1)); // For testing purposes:
    int id = userManager.saveUser(newUser, this.getCurrentUser());

    // If successfully added.
    if (id > 0) {
      newUser.setId(id);
      return true;
    } else {
      // If some error occurred.
      return false;
    }
  }

  /**
   * Create a new user in the system.
   * 
   * @return SUCCESS if user could be successfully created, INPUT if some information is needed and ERROR if some error
   *         appeared.
   * @throws Exception
   */
  public String create() throws Exception {
    if (newUser.getEmail() != null) {
      boolean emailExists = false;
      // We need to validate that the email does not exist yet into our database.
      emailExists = userManager.getUserByEmail(newUser.getEmail()) == null ? false : true;

      // If email already exists.
      if (emailExists) {
        // If email already exists into our database.
        message = this.getText("planning.manageUsers.email.existing");
        newUser = null;
        return SUCCESS; // Stop here!
      }

      // Validate if is a CGIAR email.
      if (newUser.getEmail().toLowerCase().endsWith(APConstants.OUTLOOK_EMAIL)) {
        newUser.setCcafsUser(true); // marking it as CCAFS user.

        // Validate and populate the information that is coming from the CGIAR Outlook Active Directory.
        newUser = this.validateOutlookUser(newUser.getEmail());
        // If user was not found in the Active Directory.
        if (newUser == null) {
          message = this.getText("planning.manageUsers.email.doesNotExist");
          return SUCCESS; // Stop here!
        } else {
          // If user was found, let's add it into our database.
          this.addUser();
        }
      } else {
        // If the email does not belong to the CGIAR.
        if (newUser.getFirstName() != null && newUser.getLastName() != null) {
          newUser.setCcafsUser(false);
          // Generating a random password.
          // String newPassword = RandomStringUtils.random(6, "0123456789abcdefghijkmnpqrstuvwxyz");
          String newPassword = RandomStringUtils.randomNumeric(6);
          newUser.setPassword(newPassword);
          if (!this.addUser()) {
            // If user could not be added.
            newUser = null;
            message = this.getText("planning.manageUsers.email.notAdded");
          }
          return SUCCESS;
        } else {
          message = this.getText("planning.manageUsers.email.validation");
          return SUCCESS;
        }
      }
    }
    return SUCCESS;
  }

  @Override
  public String execute() throws Exception {
    // Nothing to do here yet.
    return SUCCESS;
  }

  /**
   * Get a message of the result of the query.
   * 
   * @return a confirmation message of the result
   */
  public String getMessage() {
    return this.message;
  }

  public User getNewUser() {
    return this.newUser;
  }

  public List<User> getUsers() {
    return users;
  }

  @Override
  public void prepare() throws Exception {
    // if searching a user, we need to get the queried String.
    if (ActionContext.getContext().getName().equals("searchUsers")) {
      queryParameter = StringUtils.trim(this.getRequest().getParameter(APConstants.QUERY_PARAMETER));
    } else if (ActionContext.getContext().getName().equals("createUser")) {
      // if Adding a new user, we need to get the info to be added.
      newUser = new User();
      newUser.setId(-1);
      newUser.setFirstName(StringUtils.trim(this.getRequest().getParameter(PARAM_FIRST_NAME)));
      newUser.setLastName(StringUtils.trim(this.getRequest().getParameter(PARAM_LAST_NAME)));
      newUser.setEmail(StringUtils.trim(this.getRequest().getParameter(PARAM_EMAIL)));
      newUser.setActive(StringUtils.trim(this.getRequest().getParameter(PARAM_IS_ACTIVE)).equals("1") ? true : false);
    }

  }

  /**
   * Search a user in the database
   * 
   * @return SUCCESS if the search was successfully made.
   * @throws Exception if some error appear.
   */
  public String search() throws Exception {
    users = userManager.searchUser(queryParameter);

    LOG.info("The search of users by '{}' was made successfully.", queryParameter);
    return SUCCESS;
  }

  /**
   * Validate if a given user exists in the Outlook Active Directory .
   * 
   * @param email is the CGIAR email.
   * @return a populated user with all the information that is coming from the OAD, or null if the email does not exist.
   */
  private User validateOutlookUser(String email) {
    LDAPUser user = LDAPService.searchUserByEmail(email);
    if (user != null) {
      newUser.setFirstName(user.getFirstName());
      newUser.setLastName(user.getLastName());
      newUser.setUsername(user.getLogin());
      return newUser;
    }
    return null;
  }
}
