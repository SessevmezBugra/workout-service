package com.workout.service;

import java.util.List;

import com.workout.entity.WorkoutSectionEntity;

public interface WorkoutSectionService {

	WorkoutSectionEntity create(WorkoutSectionEntity entity);

	WorkoutSectionEntity update(WorkoutSectionEntity entity);

	void deleteById(Long id);

	WorkoutSectionEntity findById(Long id);

	List<WorkoutSectionEntity> findByWorkoutId(Long workoutId);
	
}
