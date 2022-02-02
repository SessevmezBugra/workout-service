package com.workout.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.workout.entity.WorkoutMoveEntity;

@Repository
public interface WorkoutMoveRepository  extends JpaRepository<WorkoutMoveEntity, Long>{

	Optional<List<WorkoutMoveEntity>> findBySectionId(Long sectionId);

}
