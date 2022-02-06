package com.training.service;

import java.util.List;

import org.keycloak.representations.idm.UserRepresentation;

public interface KeycloakUserService {

	UserRepresentation findByUserId(String userId);
	
	List<UserRepresentation> findByUsername(String username);
	
}
