package com.workout.service.impl;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.workout.entity.GymEntity;
import com.workout.repository.GymRepository;
import com.workout.service.GymService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GymServiceImpl implements GymService{
	
	private final GymRepository gymRepository;
	
	@Override
	public GymEntity create(GymEntity entity) {
		return gymRepository.save(entity);
	}

	@Override
	public GymEntity update(GymEntity entity) {
		GymEntity foundEntity = findById(entity.getId());
		if (!ObjectUtils.isEmpty(entity.getName())) {
			foundEntity.setName(entity.getName());
		}
		if (!ObjectUtils.isEmpty(entity.getStatus())) {
			foundEntity.setStatus(entity.getStatus());
		}
		return gymRepository.save(foundEntity);
	}

	@Override
	public void deleteById(Long id) {
		gymRepository.deleteById(id);
	}

	@Override
	public GymEntity findById(Long id) {
		return gymRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Spor salonu bulunamadi"));
	}

	@Override
	public List<GymEntity> findByUserId(String userId) {
		return gymRepository.findByUsersUserId(userId).orElse(Collections.emptyList());
	}

	@Override
	public GymEntity findByUserIdAndGymId(String userId, Long gymId) {
		return gymRepository.findByUsersUserIdAndId(userId, gymId).orElseThrow(() -> new EntityNotFoundException("Spor salonu bulunamadi"));
	}

}
