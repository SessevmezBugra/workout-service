package com.workout.service;

import java.util.List;
import java.util.Optional;

import com.workout.entity.GymUserEntity;
import com.workout.enums.UserRole;

public interface GymUserService {

	GymUserEntity create(GymUserEntity entity);

	GymUserEntity update(GymUserEntity entity);

	void deleteById(Long id);

	GymUserEntity findById(Long id);

	List<GymUserEntity> findByGymIdAndUserRole(Long gymId, UserRole role);

	Optional<GymUserEntity> findByGymIdAndUserId(Long gymId, String userId);
}
