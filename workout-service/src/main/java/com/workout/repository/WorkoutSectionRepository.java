package com.workout.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.workout.entity.WorkoutSectionEntity;

@Repository
public interface WorkoutSectionRepository  extends JpaRepository<WorkoutSectionEntity, Long>{

	Optional<List<WorkoutSectionEntity>> findByWorkoutId(Long workoutId);

}