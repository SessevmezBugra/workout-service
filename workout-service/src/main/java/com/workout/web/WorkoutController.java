package com.workout.web;

import java.util.Arrays;
import java.util.List;

import org.keycloak.representations.idm.UserRepresentation;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.workout.constant.ApiPath;
import com.workout.dto.WorkoutDto;
import com.workout.dto.WorkoutMoveDto;
import com.workout.dto.WorkoutSectionDto;
import com.workout.entity.WorkoutEntity;
import com.workout.entity.WorkoutMoveEntity;
import com.workout.entity.WorkoutSectionEntity;
import com.workout.service.KeycloakUserService;
import com.workout.service.WorkoutMoveService;
import com.workout.service.WorkoutSectionService;
import com.workout.service.WorkoutService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(ApiPath.WorkoutCtrl.CTRL)
@RequiredArgsConstructor
public class WorkoutController {

	private final ModelMapper modelMapper;
	private final WorkoutService workoutService;
	private final KeycloakUserService keycloakUserService;
	private final WorkoutSectionService workoutSectionService;
	private final WorkoutMoveService workoutMoveService;
	
	@RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<WorkoutEntity> createWorkout(@RequestBody WorkoutEntity workoutEntity){
		UserRepresentation userRepresentation = keycloakUserService.findByUserId(workoutEntity.getUserId());
		workoutEntity.setUsername(userRepresentation.getUsername());
		workoutEntity.setUserId(userRepresentation.getId());
		workoutService.create(workoutEntity);
        return ResponseEntity.ok(workoutEntity);
    }
	
	@RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<WorkoutEntity> updateWorkout(@RequestBody WorkoutEntity workoutEntity){
		workoutService.update(workoutEntity);
        return ResponseEntity.ok(workoutEntity);
    }
	
	@RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<WorkoutDto>> getWorkouts(@RequestParam String userId){
		List<WorkoutEntity> workouts = workoutService.findByUserId(userId);
		List<WorkoutDto> workoutDtos = Arrays.asList(modelMapper.map(workouts, WorkoutDto[].class));
		workoutDtos.parallelStream().forEach((w) -> {
			if(!ObjectUtils.isEmpty(w.getCreatedBy())) {
				try {
					UserRepresentation userRepresentation = keycloakUserService.findByUserId(w.getCreatedBy());
					w.setCreatorFirstName(userRepresentation.getFirstName());
					w.setCreatorLastName(userRepresentation.getLastName());
				} catch (Exception e) {
					w.setCreatorFirstName("");
					w.setCreatorLastName("");
				}
			}
		});
        return ResponseEntity.ok(workoutDtos);
    }
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<WorkoutEntity> getWorkout(@PathVariable Long id){
		WorkoutEntity workout = workoutService.findById(id);
        return ResponseEntity.ok(workout);
    }
	
	@RequestMapping(value = "/{workoutId}/section", method = RequestMethod.POST)
    public ResponseEntity<WorkoutSectionEntity> createWorkoutSection(@PathVariable Long workoutId, @RequestBody WorkoutSectionEntity workoutSectionEntity){
		WorkoutEntity workoutEntity = workoutService.findById(workoutId);
		workoutSectionEntity.setWorkout(workoutEntity);
        return ResponseEntity.ok(workoutSectionService.create(workoutSectionEntity));
    }
	
	@RequestMapping(value = "/{workoutId}/section", method = RequestMethod.PUT)
    public ResponseEntity<WorkoutSectionEntity> updateWorkoutSection(@RequestBody WorkoutSectionEntity workoutSectionEntity){
        return ResponseEntity.ok(workoutSectionService.update(workoutSectionEntity));
    }
	
	@RequestMapping(value = "/{workoutId}/section", method = RequestMethod.GET)
    public ResponseEntity<List<WorkoutSectionDto>> getWorkoutSections(@PathVariable Long workoutId){
		List<WorkoutSectionEntity> workoutSections = workoutSectionService.findByWorkoutId(workoutId);
		List<WorkoutSectionDto> workoutSectionDtos = Arrays.asList(modelMapper.map(workoutSections, WorkoutSectionDto[].class));
        return ResponseEntity.ok(workoutSectionDtos);
    }
	
	@RequestMapping(value = "/{workoutId}/section/{sectionId}/move", method = RequestMethod.POST)
    public ResponseEntity<WorkoutMoveEntity> createWorkoutMove(@PathVariable Long sectionId, @RequestBody WorkoutMoveEntity workoutMoveEntity){
		WorkoutSectionEntity foundWorkoutSectionEntity = workoutSectionService.findById(sectionId);
		workoutMoveEntity.setSection(foundWorkoutSectionEntity);
        return ResponseEntity.ok(workoutMoveService.create(workoutMoveEntity));
    }
	
	@RequestMapping(value = "/{workoutId}/section/{sectionId}/move", method = RequestMethod.PUT)
    public ResponseEntity<WorkoutMoveEntity> updateWorkoutMove(@RequestBody WorkoutMoveEntity workoutMoveEntity){
        return ResponseEntity.ok(workoutMoveService.update(workoutMoveEntity));
    }
	
	@RequestMapping(value = "/{workoutId}/section/{sectionId}/move", method = RequestMethod.GET)
    public ResponseEntity<List<WorkoutMoveDto>> getWorkoutMoves(@PathVariable Long sectionId){
		List<WorkoutMoveEntity> workoutMoves = workoutMoveService.findBySectionId(sectionId);
		List<WorkoutMoveDto> workoutMoveDtos = Arrays.asList(modelMapper.map(workoutMoves, WorkoutMoveDto[].class));
        return ResponseEntity.ok(workoutMoveDtos);
    }
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteWorkout(@PathVariable Long id){
		workoutService.deleteById(id);
        return ResponseEntity.ok().build();
    }
	
	@RequestMapping(value = "/{workoutId}/section/{sectionId}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteWorkoutSection(@PathVariable Long sectionId){
		workoutSectionService.deleteById(sectionId);
        return ResponseEntity.ok().build();
    }
	
	@RequestMapping(value = "/{workoutId}/section/{sectionId}/move/{moveId}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteWorkoutMove(@PathVariable Long moveId){
		workoutMoveService.deleteById(moveId);
        return ResponseEntity.ok().build();
    }
	
	
}
