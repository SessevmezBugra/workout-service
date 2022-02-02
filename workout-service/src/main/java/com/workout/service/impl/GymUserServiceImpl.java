package com.workout.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.workout.entity.GymUserEntity;
import com.workout.enums.UserRole;
import com.workout.repository.GymUserRepository;
import com.workout.service.GymUserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GymUserServiceImpl implements GymUserService {
	
	private final GymUserRepository gymUserRepository;
	
	@Override
	public GymUserEntity create(GymUserEntity entity) {
		return gymUserRepository.save(entity);
	}

	@Override
	public GymUserEntity update(GymUserEntity entity) {
		GymUserEntity foundEntity = findById(entity.getId());
		if (!ObjectUtils.isEmpty(entity.getRole())) {
			foundEntity.setRole(entity.getRole());
		}
		if (!ObjectUtils.isEmpty(entity.getStatus())) {
			foundEntity.setStatus(entity.getStatus());
		}
		return gymUserRepository.save(foundEntity);
	}

	@Override
	public void deleteById(Long id) {
		gymUserRepository.deleteById(id);
	}

	@Override
	public GymUserEntity findById(Long id) {
		return gymUserRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Spor salonunda boyle bir kullanici bulunamadi"));
	}

	@Override
	public List<GymUserEntity> findByGymIdAndUserRole(Long gymId, UserRole role) {
		return gymUserRepository.findByGymIdAndRole(gymId, role).orElse(Collections.emptyList());
	}

	@Override
	public Optional<GymUserEntity> findByGymIdAndUserId(Long gymId, String userId) {
		return gymUserRepository.findByGymIdAndUserId(gymId, userId);
	}

}