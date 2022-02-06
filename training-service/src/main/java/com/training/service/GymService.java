package com.training.service;

import java.util.List;

import com.training.entity.GymEntity;

public interface GymService {

	GymEntity create(GymEntity entity);
	
	GymEntity update(GymEntity entity);
	
	void deleteById(Long id);
	
	GymEntity findById(Long id);

	List<GymEntity> findByUserId(String userId);

	GymEntity findByUserIdAndGymId(String userId, Long gymId);
}
