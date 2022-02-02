package com.workout.dao.impl;

import java.util.List;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Repository;

import com.workout.dao.KeycloakUserDao;
import com.workout.dao.common.KeycloakResource;
import com.workout.model.KeycloakAdminClientConfig;


//@ConditionalOnProperty(
//        name = "custom.security.config.enabled",
//        havingValue = "true",
//        matchIfMissing = true)
@Repository
public class KeycloakUserDaoImpl extends KeycloakResource implements KeycloakUserDao {

    public KeycloakUserDaoImpl(Keycloak keycloak, KeycloakAdminClientConfig keycloakAdminClientConfig) {
        super(keycloak, keycloakAdminClientConfig);
    }


    @Override
    public List<UserRepresentation> findByUsername(String username) {
        UsersResource usersResource = getUsersResource();
        return usersResource.search(username, null, null, null, true, 0, 10, true, false);
    }

    @Override
    public UserRepresentation findByUserId(String userId) {
        UsersResource usersResource = getUsersResource();
        return usersResource.get(userId).toRepresentation();
    }


	@Override
	public void createAttribute(String userId, String key, String value) {
		UsersResource usersResource = getUsersResource();
		usersResource.get(userId).update(findByUserId(userId).singleAttribute(key, value));
	}
    
}
