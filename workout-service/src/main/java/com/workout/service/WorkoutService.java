package com.workout.service;

import java.util.List;

import com.workout.entity.WorkoutEntity;

public interface WorkoutService {

	WorkoutEntity create(WorkoutEntity entity);

	WorkoutEntity update(WorkoutEntity entity);

	void deleteById(Long id);

	WorkoutEntity findById(Long id);

	List<WorkoutEntity> findByUserId(String userId);

	List<WorkoutEntity> findByUsername(String username);
	
}
