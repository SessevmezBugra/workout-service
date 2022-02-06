package com.training.service;

import java.util.List;

import com.training.entity.TrainingEntity;

public interface TrainingService {

	TrainingEntity create(TrainingEntity entity);

	TrainingEntity update(TrainingEntity entity);

	void deleteById(Long id);

	TrainingEntity findById(Long id);

	List<TrainingEntity> findByUserId(String userId);

	List<TrainingEntity> findByUsername(String username);
	
}
