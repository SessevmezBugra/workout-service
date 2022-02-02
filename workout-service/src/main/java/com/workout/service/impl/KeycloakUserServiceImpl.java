package com.workout.service.impl;

import java.util.List;

import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import com.workout.dao.KeycloakUserDao;
import com.workout.service.KeycloakUserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KeycloakUserServiceImpl implements KeycloakUserService {

	private final KeycloakUserDao keycloakUserDao;
	
	@Override
	public UserRepresentation findByUserId(String userId) {
		return keycloakUserDao.findByUserId(userId);
	}

	@Override
	public List<UserRepresentation> findByUsername(String username) {
		return keycloakUserDao.findByUsername(username);
	}

}
