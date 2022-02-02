package com.workout.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.workout.entity.GymUserEntity;
import com.workout.enums.UserRole;

@Repository
public interface GymUserRepository extends JpaRepository<GymUserEntity, Long>{

	Optional<List<GymUserEntity>> findByGymIdAndRole(Long gymId, UserRole role);

	Optional<GymUserEntity> findByGymIdAndUsername(Long gymId, String username);

	Optional<GymUserEntity> findByGymIdAndUserId(Long gymId, String userId);

}
