package com.workout.service.impl;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.workout.entity.WorkoutSectionEntity;
import com.workout.repository.WorkoutSectionRepository;
import com.workout.service.WorkoutSectionService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WorkoutSectionServiceImpl implements WorkoutSectionService {
	
	private final WorkoutSectionRepository workoutSectionRepository;
	
	@Override
	public WorkoutSectionEntity create(WorkoutSectionEntity entity) {
		return workoutSectionRepository.save(entity);
	}

	@Override
	public WorkoutSectionEntity update(WorkoutSectionEntity entity) {
		WorkoutSectionEntity foundEntity = findById(entity.getId());
		if (!ObjectUtils.isEmpty(entity.getName())) {
			foundEntity.setName(entity.getName());
		}
		if (!ObjectUtils.isEmpty(entity.getDesc())) {
			foundEntity.setDesc(entity.getDesc());
		}
		return workoutSectionRepository.save(foundEntity);
	}

	@Override
	public void deleteById(Long id) {
		workoutSectionRepository.deleteById(id);
		
	}

	@Override
	public WorkoutSectionEntity findById(Long id) {
		return workoutSectionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Antrenmanda bu bolum bulunamadi"));
	}

	@Override
	public List<WorkoutSectionEntity> findByWorkoutId(Long workoutId) {
		return workoutSectionRepository.findByWorkoutId(workoutId).orElse(Collections.emptyList());
	}

}
