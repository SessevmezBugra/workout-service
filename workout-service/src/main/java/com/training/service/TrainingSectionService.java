package com.training.service;

import java.util.List;

import com.training.entity.TrainingSectionEntity;

public interface TrainingSectionService {

	TrainingSectionEntity create(TrainingSectionEntity entity);

	TrainingSectionEntity update(TrainingSectionEntity entity);

	void deleteById(Long id);

	TrainingSectionEntity findById(Long id);

	List<TrainingSectionEntity> findByTrainingId(Long trainingId);
	
}
