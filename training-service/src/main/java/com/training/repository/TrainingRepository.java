package com.training.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.training.entity.TrainingEntity;

@Repository
public interface TrainingRepository  extends JpaRepository<TrainingEntity, Long>{

	Optional<List<TrainingEntity>> findByUserIdOrderByCreatedDateDesc(String userId);

	Optional<List<TrainingEntity>> findByUsername(String username);

}
