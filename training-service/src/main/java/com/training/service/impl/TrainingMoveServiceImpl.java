package com.training.service.impl;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.training.entity.TrainingMoveEntity;
import com.training.repository.TrainingMoveRepository;
import com.training.service.TrainingMoveService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TrainingMoveServiceImpl implements TrainingMoveService {
	
	private final TrainingMoveRepository trainingMoveRepository;
	
	@Override
	public TrainingMoveEntity create(TrainingMoveEntity entity) {
		return trainingMoveRepository.save(entity);
	}

	@Override
	public TrainingMoveEntity update(TrainingMoveEntity entity) {
		TrainingMoveEntity foundEntity = findById(entity.getId());
		if (!ObjectUtils.isEmpty(entity.getName())) {
			foundEntity.setName(entity.getName());
		}
		if (!ObjectUtils.isEmpty(entity.getSet())) {
			foundEntity.setSet(entity.getSet());
		}
		if (!ObjectUtils.isEmpty(entity.getRepetition())) {
			foundEntity.setRepetition(entity.getRepetition());
		}
		return trainingMoveRepository.save(foundEntity);
	}

	@Override
	public void deleteById(Long id) {
		trainingMoveRepository.deleteById(id);
		
	}

	@Override
	public TrainingMoveEntity findById(Long id) {
		return trainingMoveRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Antrenmanda boyle bir hareket bulunamadi"));
	}

	@Override
	public List<TrainingMoveEntity> findBySectionId(Long sectionId) {
		return trainingMoveRepository.findBySectionId(sectionId).orElse(Collections.emptyList());
	}

}
