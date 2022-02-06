package com.training.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.training.entity.TrainingMoveEntity;

@Repository
public interface TrainingMoveRepository  extends JpaRepository<TrainingMoveEntity, Long>{

	Optional<List<TrainingMoveEntity>> findBySectionId(Long sectionId);

}
