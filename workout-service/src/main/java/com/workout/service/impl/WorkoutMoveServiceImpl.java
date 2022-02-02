package com.workout.service.impl;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.workout.entity.WorkoutMoveEntity;
import com.workout.repository.WorkoutMoveRepository;
import com.workout.service.WorkoutMoveService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WorkoutMoveServiceImpl implements WorkoutMoveService {
	
	private final WorkoutMoveRepository workoutMoveRepository;
	
	@Override
	public WorkoutMoveEntity create(WorkoutMoveEntity entity) {
		return workoutMoveRepository.save(entity);
	}

	@Override
	public WorkoutMoveEntity update(WorkoutMoveEntity entity) {
		WorkoutMoveEntity foundEntity = findById(entity.getId());
		if (!ObjectUtils.isEmpty(entity.getName())) {
			foundEntity.setName(entity.getName());
		}
		if (!ObjectUtils.isEmpty(entity.getSet())) {
			foundEntity.setSet(entity.getSet());
		}
		if (!ObjectUtils.isEmpty(entity.getRepetition())) {
			foundEntity.setRepetition(entity.getRepetition());
		}
		return workoutMoveRepository.save(foundEntity);
	}

	@Override
	public void deleteById(Long id) {
		workoutMoveRepository.deleteById(id);
		
	}

	@Override
	public WorkoutMoveEntity findById(Long id) {
		return workoutMoveRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Antrenmanda boyle bir hareket bulunamadi"));
	}

	@Override
	public List<WorkoutMoveEntity> findBySectionId(Long sectionId) {
		return workoutMoveRepository.findBySectionId(sectionId).orElse(Collections.emptyList());
	}

}
