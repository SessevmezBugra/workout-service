package com.workout.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.workout.entity.WorkoutEntity;

@Repository
public interface WorkoutRepository  extends JpaRepository<WorkoutEntity, Long>{

	Optional<List<WorkoutEntity>> findByUserIdOrderByCreatedDateDesc(String userId);

	Optional<List<WorkoutEntity>> findByUsername(String username);

}
