package com.training.service;

import java.util.List;
import java.util.Optional;

import com.training.entity.GymUserEntity;
import com.training.enums.UserRole;

public interface GymUserService {

	GymUserEntity create(GymUserEntity entity);

	GymUserEntity update(GymUserEntity entity);

	void deleteById(Long id);

	GymUserEntity findById(Long id);

	List<GymUserEntity> findByGymIdAndUserRole(Long gymId, UserRole role);

	Optional<GymUserEntity> findByGymIdAndUserId(Long gymId, String userId);
}
