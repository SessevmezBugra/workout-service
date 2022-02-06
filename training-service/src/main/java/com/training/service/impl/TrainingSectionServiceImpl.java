package com.training.service.impl;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.training.entity.TrainingSectionEntity;
import com.training.repository.TrainingSectionRepository;
import com.training.service.TrainingSectionService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TrainingSectionServiceImpl implements TrainingSectionService {
	
	private final TrainingSectionRepository trainingSectionRepository;
	
	@Override
	public TrainingSectionEntity create(TrainingSectionEntity entity) {
		return trainingSectionRepository.save(entity);
	}

	@Override
	public TrainingSectionEntity update(TrainingSectionEntity entity) {
		TrainingSectionEntity foundEntity = findById(entity.getId());
		if (!ObjectUtils.isEmpty(entity.getName())) {
			foundEntity.setName(entity.getName());
		}
		if (!ObjectUtils.isEmpty(entity.getDesc())) {
			foundEntity.setDesc(entity.getDesc());
		}
		return trainingSectionRepository.save(foundEntity);
	}

	@Override
	public void deleteById(Long id) {
		trainingSectionRepository.deleteById(id);
		
	}

	@Override
	public TrainingSectionEntity findById(Long id) {
		return trainingSectionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Antrenmanda bu bolum bulunamadi"));
	}

	@Override
	public List<TrainingSectionEntity> findByTrainingId(Long trainingId) {
		return trainingSectionRepository.findByTrainingId(trainingId).orElse(Collections.emptyList());
	}

}
