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
import org.cgiar.ccafs.ap.data.manager.impl.LiaisonInstitutionManagerImpl;
import org.cgiar.ccafs.ap.data.model.LiaisonInstitution;
import org.cgiar.ccafs.security.authentication.Authenticator;
import org.cgiar.ccafs.security.data.manager.ProjectSpecificUserRoleManagerImpl;
import org.cgiar.ccafs.security.data.manager.UserManagerImpl;
import org.cgiar.ccafs.security.data.manager.UserRoleManagerImpl;
import org.cgiar.ccafs.security.data.model.ProjectUserRole;
import org.cgiar.ccafs.security.data.model.User;
import org.cgiar.ccafs.security.data.model.UserRole;
import org.cgiar.ccafs.utils.APConfig;

import java.util.ArrayList;
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
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Hernán David Carvajal
 * @author Héctor Fabio Tobón R. - CIAT/CCAFS
 */

public class APCustomRealm extends AuthorizingRealm {

  // Logger
  public static Logger LOG = LoggerFactory.getLogger(APCustomRealm.class);

  // Variables
  final AllowAllCredentialsMatcher credentialsMatcher = new AllowAllCredentialsMatcher();
  // private SimpleAuthorizationInfo authorizationInfo;
  private int userID;

  // Managers
  private UserManagerImpl userManager;
  private LiaisonInstitutionManagerImpl liaisonInstitutionManager;
  private UserRoleManagerImpl userRoleManager;
  private ProjectSpecificUserRoleManagerImpl projectSpecificUserRoleManager;
  private APConfig config;

  @Named("DB")
  Authenticator dbAuthenticator;

  @Named("LDAP")
  Authenticator ldapAuthenticator;
  Injector injector;


  @Inject
  public APCustomRealm(UserManagerImpl userManager, UserRoleManagerImpl userRoleManager,
    ProjectSpecificUserRoleManagerImpl projectSpecificUserRoleManager, @Named("DB") Authenticator dbAuthenticator,
    @Named("LDAP") Authenticator ldapAuthenticator, APConfig config,
    LiaisonInstitutionManagerImpl liaisonInstitutionManager) {
    super(new MemoryConstrainedCacheManager());
    this.userManager = userManager;
    this.userRoleManager = userRoleManager;
    this.projectSpecificUserRoleManager = projectSpecificUserRoleManager;
    this.dbAuthenticator = dbAuthenticator;
    this.ldapAuthenticator = ldapAuthenticator;
    this.liaisonInstitutionManager = liaisonInstitutionManager;
    injector = Guice.createInjector();
    this.config = config;
    this.setName("APCustomRealm");
  }

  @Override
  public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
    super.clearCachedAuthorizationInfo(principals);
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
    SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

    userID = (Integer) principals.getPrimaryPrincipal();
    List<UserRole> roles = userRoleManager.getUserRolesByUserID(String.valueOf(userID));
    List<Map<String, UserRole>> projectRoles = new ArrayList<>();
    List<Integer> liaisonInstitutionIDs = new ArrayList<>();

    if (roles.size() == 0) {
      roles.add(userRoleManager.getUserRole(8)); // Getting the Guest Role.
    }
    // Get the roles general to the platform
    for (UserRole role : roles) {
      authorizationInfo.addRole(role.getAcronym());
      switch (role.getId()) {
        case APConstants.ROLE_ADMIN:
          for (String permission : role.getPermissions()) {
            authorizationInfo.addStringPermission(permission);
          }
          break;
        case APConstants.ROLE_FINANCING_PROJECT:
          for (String permission : role.getPermissions()) {
            permission = permission.replace("projects:", "projects:*:");
            authorizationInfo.addStringPermission(permission);
          }
          break;
        case APConstants.ROLE_MANAGEMENT_LIAISON:
        case APConstants.ROLE_COORDINATING_UNIT:
          projectRoles.add(userRoleManager.getManagementLiaisonProjects(userID));
          break;
        case APConstants.ROLE_PROJECT_LEADER:

          projectRoles.add(userRoleManager.getProjectLeaderProjects(userID));
          break;

        case APConstants.ROLE_EXTERNAL_EVALUATOR:


          projectRoles.add(userRoleManager.getProjectExternalEvaluator(userID));
          break;

        case APConstants.ROLE_PROGRAM_DIRECTOR_EVALUATOR:
          projectRoles.add(userRoleManager.getProjectProgramDirector(userID));
          break;


        case APConstants.ROLE_PROJECT_COORDINATOR:
          projectRoles.add(userRoleManager.getProjectCordinatorProjects(userID));
          break;
        case APConstants.ROLE_CONTACT_POINT:
          projectRoles.add(userRoleManager.getContactPointProjects(userID));
          liaisonInstitutionIDs.addAll(userRoleManager.getLiaisonInstitutionID(userID));
          break;

        case APConstants.ROLE_REGIONAL_PROGRAM_LEADER:
        case APConstants.ROLE_FLAGSHIP_PROGRAM_LEADER:
          liaisonInstitutionIDs.addAll(userRoleManager.getLiaisonInstitutionID(userID));

          for (Integer liaisonInstitutionID : liaisonInstitutionIDs) {
            final LiaisonInstitution currentLiaisonInstitution =
              liaisonInstitutionManager.getLiaisonInstitution(liaisonInstitutionID);
            if (currentLiaisonInstitution.getIpProgram() == null) {
              currentLiaisonInstitution.setIpProgram("1");
            }
            projectRoles
              .add(userRoleManager.getProgramProjects(Integer.parseInt(currentLiaisonInstitution.getIpProgram())));
          }


          break;
      }
    }
    boolean addPermission = true;
    // Adding the permissions for each role exactly as they come from the database:
    for (UserRole role : roles) {
      for (String myPermission : role.getPermissions()) {
        addPermission = true;
        if (myPermission.startsWith("planning:projects:")) {
          if ((config.isPlanningClosed() && !role.getId().equals(APConstants.ROLE_ADMIN))) {
            addPermission = false;
          }
        }
        if (myPermission.startsWith("reporting:projects:")) {
          if ((config.isReportingClosed() && !role.getId().equals(APConstants.ROLE_ADMIN))) {
            addPermission = false;
          }
        }
        if (addPermission) {
          authorizationInfo.addStringPermission(myPermission);
        }
      }
    }
    // Converting those general roles into specific for the PROJECTS where they are able to edit.
    for (Map<String, UserRole> mapRoles : projectRoles) {
      for (Map.Entry<String, UserRole> entry : mapRoles.entrySet()) {
        String projectID = entry.getKey();
        UserRole role = entry.getValue();

        for (String permission : role.getPermissions()) {

          // Add the project identifier to the permission only if the permission is not at project level.
          // The following permission will be ignored: planning:projects:5:description:update
          // if (!permission.matches("((?:project:[\0-9]{1,10}:)")) {
          if (permission.contains(":projects:")) {
            permission = permission.replace("projects:", "projects:" + projectID + ":");

          }

          addPermission = true;
          if (permission.startsWith("planning:projects:")) {
            if ((config.isPlanningClosed() && !role.getId().equals(APConstants.ROLE_ADMIN))) {
              addPermission = false;
            }
          }

          if (permission.startsWith("reporting:projects:")) {
            if ((config.isReportingClosed() && !role.getId().equals(APConstants.ROLE_ADMIN))) {
              addPermission = false;
            }
          }

          if (addPermission) {
            authorizationInfo.addStringPermission(permission);
          }
          // }
        }
      }
    }
    if (authorizationInfo.getStringPermissions() != null) {
      // Converting those general roles into specific for the SYNTHESIS where they are able to edit.
      List<String> newPermissions = new ArrayList<>();
      for (String permission : authorizationInfo.getStringPermissions()) {
        for (int liaisonInstitutionID : liaisonInstitutionIDs) {
          if (permission.startsWith("reporting:synthesis:")) {
            newPermissions.add(permission.replace("synthesis:", "synthesis:" + liaisonInstitutionID + ":"));
          }
        }
      }
      authorizationInfo.addStringPermissions(newPermissions);
    }

    if (!config.isClosed()) {
      // Getting the specific roles based on the table project_roles.
      List<ProjectUserRole> projectSpecificUserRoles =
        projectSpecificUserRoleManager.getProjectSpecificUserRoles(userID);
      // Adding the specific project roles to the user.
      for (ProjectUserRole projectUserRole : projectSpecificUserRoles) {
        for (String permission : projectUserRole.getUserRole().getPermissions()) {
          if (permission.contains(":projects:")) {
            permission = permission.replace("projects:", "projects:" + projectUserRole.getProjectID() + ":");
          }
          authorizationInfo.addStringPermission(permission);
        }
      }
    }
    return authorizationInfo;
  }

  @Override
  public CredentialsMatcher getCredentialsMatcher() {
    return credentialsMatcher;
  }
}
