package com.training.service.impl;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.training.entity.TrainingEntity;
import com.training.repository.TrainingRepository;
import com.training.service.TrainingService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TrainingServiceImpl implements TrainingService {
	
	private final TrainingRepository trainingRepository;

	@Override
	public TrainingEntity create(TrainingEntity entity) {
		return trainingRepository.save(entity);
	}

	@Override
	public TrainingEntity update(TrainingEntity entity) {
		TrainingEntity foundEntity = findById(entity.getId());
		if (!ObjectUtils.isEmpty(entity.getName())) {
			foundEntity.setName(entity.getName());
		}
		if (!ObjectUtils.isEmpty(entity.getDesc())) {
			foundEntity.setDesc(entity.getDesc());
		}
		return trainingRepository.save(foundEntity);
	}

	@Override
	public void deleteById(Long id) {
		trainingRepository.deleteById(id);
		
	}

	@Override
	public TrainingEntity findById(Long id) {
		return trainingRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Antrenman bulunamadi"));
	}

	@Override
	public List<TrainingEntity> findByUserId(String userId) {
		return trainingRepository.findByUserIdOrderByCreatedDateDesc(userId).orElse(Collections.emptyList());
	}

	@Override
	public List<TrainingEntity> findByUsername(String username) {
		return trainingRepository.findByUsername(username).orElse(Collections.emptyList());
	}
	
}
