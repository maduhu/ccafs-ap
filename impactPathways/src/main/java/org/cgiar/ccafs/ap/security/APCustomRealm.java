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

package org.cgiar.ccafs.ap.security;

import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.ProjectManager;
import org.cgiar.ccafs.security.authentication.Authenticator;
import org.cgiar.ccafs.security.data.manager.UserManagerImpl;
import org.cgiar.ccafs.security.data.manager.UserRoleManagerImpl;
import org.cgiar.ccafs.security.data.model.User;
import org.cgiar.ccafs.security.data.model.UserRole;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.name.Named;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.AllowAllCredentialsMatcher;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Hernán David Carvajal
 */

public class APCustomRealm extends AuthorizingRealm {

  // Logger
  public static Logger LOG = LoggerFactory.getLogger(APCustomRealm.class);

  // Variables
  final AllowAllCredentialsMatcher credentialsMatcher = new AllowAllCredentialsMatcher();
  private UserManagerImpl userManager;
  private UserRoleManagerImpl userRoleManager;
  private ProjectManager projectManager;

  @Named("DB")
  Authenticator dbAuthenticator;

  @Named("LDAP")
  Authenticator ldapAuthenticator;
  Injector injector;


  @Inject
  public APCustomRealm(UserManagerImpl userManager, UserRoleManagerImpl userRoleManager, ProjectManager projectManager,
    @Named("DB") Authenticator dbAuthenticator, @Named("LDAP") Authenticator ldapAuthenticator) {
    this.userManager = userManager;
    this.userRoleManager = userRoleManager;
    this.projectManager = projectManager;
    this.dbAuthenticator = dbAuthenticator;
    this.ldapAuthenticator = ldapAuthenticator;
    injector = Guice.createInjector();
    this.setName("APCustomRealm");
  }

  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
    // identify account to log to
    UsernamePasswordToken userPassToken = (UsernamePasswordToken) token;
    final String username = userPassToken.getUsername();
    final String password = new String(userPassToken.getPassword());
    User user;
    boolean authenticated = false;

    // Get user info from db
    if (username.contains("@")) {
      user = userManager.getUserByEmail(username);
    } else {
      // if user is loggin with his username, we must attach the cgiar.org.
      user = userManager.getUserByUsername(username);
    }

    if (user == null) {
      LOG.info("doGetAuthenticationInfo() > No account found for user {}.", username);
      throw new UnknownAccountException();
    }

    if (!user.isActive()) {
      LOG.info("doGetAuthenticationInfo() > Account of user {} is NOT active.", username);
      throw new LockedAccountException();
    }

    if (user.isCcafsUser()) {
      authenticated = ldapAuthenticator.authenticate(user.getUsername(), password);
    } else {
      authenticated = dbAuthenticator.authenticate(user.getEmail(), password);
    }

    if (!authenticated) {
      throw new IncorrectCredentialsException();
    }

    SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user.getId(), user.getPassword(), this.getName());
    return info;
  }

  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
    int userID = (Integer) principals.getPrimaryPrincipal();
    SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
    List<UserRole> roles = userRoleManager.getUserRolesByUserID(String.valueOf(userID));
    Map<String, UserRole> projectRoles = new HashMap<>();


    // Get the roles general to the platform
    for (UserRole role : roles) {
      authorizationInfo.addRole(role.getAcronym());

      switch (role.getId()) {
        case APConstants.ROLE_ADMIN:
          for (String permission : role.getPermissions()) {
            authorizationInfo.addStringPermission(permission);
          }
          break;

        case APConstants.ROLE_MANAGEMENT_LIAISON:
          projectRoles.putAll(userRoleManager.getManagementLiaisonProjects(userID));
          break;

        case APConstants.ROLE_PROJECT_LEADER:
        case APConstants.ROLE_PROJECT_COORDINATOR:
          projectRoles.putAll(userRoleManager.getProjectLeaderProjects(userID));
          break;

        case APConstants.ROLE_CONTACT_POINT:
          projectRoles.putAll(userRoleManager.getContactPointProjects(userID));
          break;
      }
    }

    // Get the roles specific to the projects
    for (Map.Entry<String, UserRole> entry : projectRoles.entrySet()) {
      String projectID = entry.getKey();
      UserRole role = entry.getValue();

      for (String permission : role.getPermissions()) {
        // Add the project identifier to the permission
        String projectPermission = permission.replace("projects:", "projects:" + projectID + ":");
        authorizationInfo.addStringPermission(projectPermission);
      }
    }

    return authorizationInfo;
  }

  @Override
  public CredentialsMatcher getCredentialsMatcher() {
    return credentialsMatcher;
  }
}