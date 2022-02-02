package com.workout.service.impl;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.workout.entity.WorkoutEntity;
import com.workout.repository.WorkoutRepository;
import com.workout.service.WorkoutService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WorkoutServiceImpl implements WorkoutService {
	
	private final WorkoutRepository workoutRepository;

	@Override
	public WorkoutEntity create(WorkoutEntity entity) {
		return workoutRepository.save(entity);
	}

	@Override
	public WorkoutEntity update(WorkoutEntity entity) {
		WorkoutEntity foundEntity = findById(entity.getId());
		if (!ObjectUtils.isEmpty(entity.getName())) {
			foundEntity.setName(entity.getName());
		}
		if (!ObjectUtils.isEmpty(entity.getDesc())) {
			foundEntity.setDesc(entity.getDesc());
		}
		return workoutRepository.save(foundEntity);
	}

	@Override
	public void deleteById(Long id) {
		workoutRepository.deleteById(id);
		
	}

	@Override
	public WorkoutEntity findById(Long id) {
		return workoutRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Antrenman bulunamadi"));
	}

	@Override
	public List<WorkoutEntity> findByUserId(String userId) {
		return workoutRepository.findByUserIdOrderByCreatedDateDesc(userId).orElse(Collections.emptyList());
	}

	@Override
	public List<WorkoutEntity> findByUsername(String username) {
		return workoutRepository.findByUsername(username).orElse(Collections.emptyList());
	}
	
}
