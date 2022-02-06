package com.training.web;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.keycloak.representations.idm.UserRepresentation;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.training.constant.ApiPath;
import com.training.dto.GymUserDto;
import com.training.entity.GymEntity;
import com.training.entity.GymUserEntity;
import com.training.enums.UserRole;
import com.training.enums.UserStatus;
import com.training.service.GymService;
import com.training.service.GymUserService;
import com.training.service.KeycloakUserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(ApiPath.GymUserCtrl.CTRL)
@RequiredArgsConstructor
public class GymUserController {
	
	private final GymUserService gymUserService;
	private final ModelMapper modelMapper;
	private final KeycloakUserService keycloakUserService;
	private final GymService gymService;
	
	
	@RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<GymUserDto>> getGymUsers(@RequestParam Long gymId, @RequestParam UserRole userRole){
		List<GymUserEntity> users = gymUserService.findByGymIdAndUserRole(gymId, userRole);
		List<GymUserDto> usersDto = Arrays.asList(modelMapper.map(users, GymUserDto[].class));
		usersDto.forEach(userDto -> {
			UserRepresentation userRepresentation = keycloakUserService.findByUserId(userDto.getUserId());
			userDto.setFirstName((userRepresentation.getFirstName()));
			userDto.setLastName((userRepresentation.getLastName()));
		});
        return ResponseEntity.ok(usersDto);
    }
	
	@RequestMapping(value = "/invite", method = RequestMethod.GET)
    public ResponseEntity<Void> inviteUsersToGym(@RequestParam Long gymId, @RequestParam UserRole userRole, @RequestParam Optional<List<String>> userIds){
		userIds.ifPresent((uIds) -> {
			uIds.forEach((uId) -> {
				Optional<GymUserEntity> foundEntity = gymUserService.findByGymIdAndUserId(gymId, uId);
				foundEntity.ifPresentOrElse((gymUser) -> {
					gymUser.setRole(userRole);
					gymUser.setStatus(UserStatus.INVITED);
					gymUserService.update(gymUser);
				}, () -> {
					UserRepresentation foundUserRepresentation = keycloakUserService.findByUserId(uId);
					GymEntity foundGym = gymService.findById(gymId);
					GymUserEntity gymUserEntity = new GymUserEntity();
					gymUserEntity.setRole(userRole);
					gymUserEntity.setStatus(UserStatus.INVITED);
					gymUserEntity.setUserId(foundUserRepresentation.getId());
					gymUserEntity.setUsername(foundUserRepresentation.getUsername());
					gymUserEntity.setGym(foundGym);
					gymUserService.create(gymUserEntity);
				});
			});
		});
        return ResponseEntity.ok().build();
    }
}
