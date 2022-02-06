package com.training.web;

import java.util.Arrays;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.training.constant.ApiPath;
import com.training.dto.GymDto;
import com.training.entity.GymEntity;
import com.training.entity.GymUserEntity;
import com.training.enums.GymStatus;
import com.training.enums.UserRole;
import com.training.enums.UserStatus;
import com.training.service.GymService;
import com.training.util.CurrentUserProvider;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(ApiPath.GymCtrl.CTRL)
@RequiredArgsConstructor
public class GymController {

	private final GymService gymService;
	private final ModelMapper modelMapper;
	private final CurrentUserProvider currentUserProvider;
	
	@RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<GymDto> createGym(@RequestBody GymEntity gymEntity){
		GymUserEntity gymUserEntity = new GymUserEntity();
		gymUserEntity.setRole(UserRole.CO_FOUNDER);
		gymUserEntity.setStatus(UserStatus.ACTIVE);
		gymUserEntity.setUserId(currentUserProvider.getCurrentUser().getUserId());
		gymUserEntity.setUsername(currentUserProvider.getCurrentUser().getUsername());
		gymUserEntity.setGym(gymEntity);
		gymEntity.getUsers().add(gymUserEntity);
		gymEntity.setStatus(GymStatus.ACTIVE);
		gymService.create(gymEntity);
		GymDto gymDto = modelMapper.map(gymEntity, GymDto.class);
        return ResponseEntity.ok(gymDto);
    }
	
	@RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<GymDto>> getGymsByLoggedUser(){
		List<GymEntity> gymEntities = gymService.findByUserId(currentUserProvider.getCurrentUser().getUserId());
		List<GymDto> gymDtos = Arrays.asList(modelMapper.map(gymEntities, GymDto[].class));
        return ResponseEntity.ok(gymDtos);
    }
	
	@RequestMapping(value = "/{gymId}", method = RequestMethod.GET)
    public ResponseEntity<List<GymDto>> getGymByGymIdAndLoggedUser(@PathVariable Long gymId){
		GymEntity gymEntities = gymService.findByUserIdAndGymId(currentUserProvider.getCurrentUser().getUserId(), gymId);
		List<GymDto> gymDtos = Arrays.asList(modelMapper.map(gymEntities, GymDto.class));
        return ResponseEntity.ok(gymDtos);
    }
	
}
