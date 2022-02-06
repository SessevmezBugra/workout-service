package com.training.service;

import java.util.List;

import com.training.entity.TrainingMoveEntity;

public interface TrainingMoveService {

	TrainingMoveEntity create(TrainingMoveEntity entity);

	TrainingMoveEntity update(TrainingMoveEntity entity);

	void deleteById(Long id);

	TrainingMoveEntity findById(Long id);

	List<TrainingMoveEntity> findBySectionId(Long sectionId);
	
}
