package com.training.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.keycloak.representations.idm.UserRepresentation;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.training.constant.ApiPath;
import com.training.dto.GymDto;
import com.training.dto.UserDto;
import com.training.entity.GymEntity;
import com.training.entity.GymUserEntity;
import com.training.enums.GymStatus;
import com.training.enums.UserRole;
import com.training.enums.UserStatus;
import com.training.service.GymService;
import com.training.service.GymUserService;
import com.training.service.KeycloakUserService;
import com.training.util.CurrentUserProvider;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(ApiPath.GymCtrl.CTRL)
@RequiredArgsConstructor
public class GymController {

	private final GymService gymService;
	private final ModelMapper modelMapper;
	private final CurrentUserProvider currentUserProvider;
	private final GymUserService gymUserService;
	private final KeycloakUserService keycloakUserService;

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<GymDto> createGym(@RequestBody GymEntity gymEntity) {
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
	public ResponseEntity<List<GymDto>> getGymsByLoggedUser() {
		List<GymEntity> gymEntities = gymService.findByUserId(currentUserProvider.getCurrentUser().getUserId());
		List<GymDto> gymDtos = Arrays.asList(modelMapper.map(gymEntities, GymDto[].class));
		gymDtos.forEach((gymDto) -> {
			Optional<GymUserEntity> gymUserEntity = gymUserService.findByGymIdAndUserId(gymDto.getId(), currentUserProvider.getCurrentUser().getUserId());
			gymUserEntity.ifPresent((gymUser) -> {
				gymDto.setUserRole(gymUser.getRole());
				gymDto.setUserStatus(gymUser.getStatus());
			});
		});
		return ResponseEntity.ok(gymDtos);
	}

	@RequestMapping(value = "/{gymId}", method = RequestMethod.GET)
	public ResponseEntity<GymDto> getGymByGymId(@PathVariable Long gymId) {
		GymDto gymDto = modelMapper.map(gymService.findById(gymId), GymDto.class);
		return ResponseEntity.ok(gymDto);
	}

	@RequestMapping(value = "/{gymId}/user", method = RequestMethod.GET)
	public ResponseEntity<List<UserDto>> getGymUsers(@PathVariable Long gymId, @RequestParam UserRole userRole) {
		List<GymUserEntity> gymUserEntities = gymUserService.findByGymIdAndUserRole(gymId, userRole);
		List<UserDto> userDtos = new ArrayList<>();
		gymUserEntities.forEach(gymUserEntity -> {
			UserRepresentation userRepresentation = keycloakUserService.findByUserId(gymUserEntity.getUserId());
			UserDto userDto = modelMapper.map(userRepresentation, UserDto.class);
			userDto.setRole(gymUserEntity.getRole());
			userDto.setStatus(gymUserEntity.getStatus());
			userDtos.add(userDto);
		});
		return ResponseEntity.ok(userDtos);
	}

	@RequestMapping(value = "/{gymId}/user/{userId}", method = RequestMethod.GET)
	public ResponseEntity<UserDto> getGymUserByGymIdAndUserId(@PathVariable Long gymId, @PathVariable String userId) {
		UserRepresentation userRepresentation = keycloakUserService.findByUserId(userId);
		UserDto userDto = modelMapper.map(userRepresentation, UserDto.class);
		GymUserEntity gymUserEntity = gymUserService.findByGymIdAndUserId(gymId, userId)
				.orElseThrow(() -> new EntityNotFoundException("Bu kullanici spor salonunda mevcut degildir."));
		userDto.setRole(gymUserEntity.getRole());
		userDto.setStatus(gymUserEntity.getStatus());
		return ResponseEntity.ok(userDto);
	}

	@RequestMapping(value = "/{gymId}/invite", method = RequestMethod.GET)
	public ResponseEntity<Void> inviteUsersToGym(@PathVariable Long gymId, @RequestParam UserRole userRole,
			@RequestParam Optional<List<String>> userIds) {
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
	
	@RequestMapping(value = "/{gymId}/accept-invitation", method = RequestMethod.PUT)
	public ResponseEntity<Void> getGymUserByGymIdAndUserId(@PathVariable Long gymId) {
		gymUserService.findByGymIdAndUserId(gymId, currentUserProvider.getCurrentUser().getUserId()).ifPresent((gymUser) -> {
			gymUser.setStatus(UserStatus.ACTIVE);
			gymUserService.update(gymUser);
		});
		return ResponseEntity.ok().build();
	}

}
