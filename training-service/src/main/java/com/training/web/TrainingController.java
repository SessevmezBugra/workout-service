package com.training.web;

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

import com.training.constant.ApiPath;
import com.training.dto.TrainingDto;
import com.training.dto.TrainingMoveDto;
import com.training.dto.TrainingSectionDto;
import com.training.entity.TrainingEntity;
import com.training.entity.TrainingMoveEntity;
import com.training.entity.TrainingSectionEntity;
import com.training.service.KeycloakUserService;
import com.training.service.TrainingMoveService;
import com.training.service.TrainingSectionService;
import com.training.service.TrainingService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(ApiPath.TrainingCtrl.CTRL)
@RequiredArgsConstructor
public class TrainingController {

	private final ModelMapper modelMapper;
	private final TrainingService trainingService;
	private final KeycloakUserService keycloakUserService;
	private final TrainingSectionService trainingSectionService;
	private final TrainingMoveService trainingMoveService;
	
	@RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<TrainingEntity> createTraining(@RequestBody TrainingEntity trainingEntity){
		UserRepresentation userRepresentation = keycloakUserService.findByUserId(trainingEntity.getUserId());
		trainingEntity.setUsername(userRepresentation.getUsername());
		trainingEntity.setUserId(userRepresentation.getId());
		trainingService.create(trainingEntity);
        return ResponseEntity.ok(trainingEntity);
    }
	
	@RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<TrainingEntity> updateTraining(@RequestBody TrainingEntity trainingEntity){
		trainingService.update(trainingEntity);
        return ResponseEntity.ok(trainingEntity);
    }
	
	@RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<TrainingDto>> getTrainings(@RequestParam String userId){
		List<TrainingEntity> trainings = trainingService.findByUserId(userId);
		List<TrainingDto> trainingDtos = Arrays.asList(modelMapper.map(trainings, TrainingDto[].class));
		trainingDtos.parallelStream().forEach((w) -> {
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
        return ResponseEntity.ok(trainingDtos);
    }
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<TrainingEntity> getTraining(@PathVariable Long id){
		TrainingEntity training = trainingService.findById(id);
        return ResponseEntity.ok(training);
    }
	
	@RequestMapping(value = "/{trainingId}/section", method = RequestMethod.POST)
    public ResponseEntity<TrainingSectionEntity> createTrainingSection(@PathVariable Long trainingId, @RequestBody TrainingSectionEntity trainingSectionEntity){
		TrainingEntity trainingEntity = trainingService.findById(trainingId);
		trainingSectionEntity.setTraining(trainingEntity);
        return ResponseEntity.ok(trainingSectionService.create(trainingSectionEntity));
    }
	
	@RequestMapping(value = "/{trainingId}/section", method = RequestMethod.PUT)
    public ResponseEntity<TrainingSectionEntity> updateTrainingSection(@RequestBody TrainingSectionEntity trainingSectionEntity){
        return ResponseEntity.ok(trainingSectionService.update(trainingSectionEntity));
    }
	
	@RequestMapping(value = "/{trainingId}/section", method = RequestMethod.GET)
    public ResponseEntity<List<TrainingSectionDto>> getTrainingSections(@PathVariable Long trainingId){
		List<TrainingSectionEntity> trainingSections = trainingSectionService.findByTrainingId(trainingId);
		List<TrainingSectionDto> trainingSectionDtos = Arrays.asList(modelMapper.map(trainingSections, TrainingSectionDto[].class));
        return ResponseEntity.ok(trainingSectionDtos);
    }
	
	@RequestMapping(value = "/{trainingId}/section/{sectionId}/move", method = RequestMethod.POST)
    public ResponseEntity<TrainingMoveEntity> createTrainingMove(@PathVariable Long sectionId, @RequestBody TrainingMoveEntity trainingMoveEntity){
		TrainingSectionEntity foundTrainingSectionEntity = trainingSectionService.findById(sectionId);
		trainingMoveEntity.setSection(foundTrainingSectionEntity);
        return ResponseEntity.ok(trainingMoveService.create(trainingMoveEntity));
    }
	
	@RequestMapping(value = "/{trainingId}/section/{sectionId}/move", method = RequestMethod.PUT)
    public ResponseEntity<TrainingMoveEntity> updateTrainingMove(@RequestBody TrainingMoveEntity trainingMoveEntity){
        return ResponseEntity.ok(trainingMoveService.update(trainingMoveEntity));
    }
	
	@RequestMapping(value = "/{trainingId}/section/{sectionId}/move", method = RequestMethod.GET)
    public ResponseEntity<List<TrainingMoveDto>> getTrainingMoves(@PathVariable Long sectionId){
		List<TrainingMoveEntity> trainingMoves = trainingMoveService.findBySectionId(sectionId);
		List<TrainingMoveDto> trainingMoveDtos = Arrays.asList(modelMapper.map(trainingMoves, TrainingMoveDto[].class));
        return ResponseEntity.ok(trainingMoveDtos);
    }
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteTraining(@PathVariable Long id){
		trainingService.deleteById(id);
        return ResponseEntity.ok().build();
    }
	
	@RequestMapping(value = "/{trainingId}/section/{sectionId}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteTrainingSection(@PathVariable Long sectionId){
		trainingSectionService.deleteById(sectionId);
        return ResponseEntity.ok().build();
    }
	
	@RequestMapping(value = "/{trainingId}/section/{sectionId}/move/{moveId}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteTrainingMove(@PathVariable Long moveId){
		trainingMoveService.deleteById(moveId);
        return ResponseEntity.ok().build();
    }
	
	
}
