package com.training.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.training.entity.TrainingSectionEntity;

@Repository
public interface TrainingSectionRepository  extends JpaRepository<TrainingSectionEntity, Long>{

	Optional<List<TrainingSectionEntity>> findByTrainingId(Long trainingId);

}