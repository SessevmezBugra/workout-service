package com.workout.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.workout.entity.GymEntity;

@Repository
public interface GymRepository extends JpaRepository<GymEntity, Long>{

	Optional<List<GymEntity>> findByUsersUserId(String userId);

	Optional<GymEntity> findByUsersUserIdAndId(String userId, Long gymId);

}
