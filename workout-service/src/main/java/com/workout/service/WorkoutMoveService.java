package com.workout.service;

import java.util.List;

import com.workout.entity.WorkoutMoveEntity;

public interface WorkoutMoveService {

	WorkoutMoveEntity create(WorkoutMoveEntity entity);

	WorkoutMoveEntity update(WorkoutMoveEntity entity);

	void deleteById(Long id);

	WorkoutMoveEntity findById(Long id);

	List<WorkoutMoveEntity> findBySectionId(Long sectionId);
	
}
