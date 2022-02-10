package com.training.web;

import java.util.Arrays;
import java.util.List;

import org.keycloak.representations.idm.UserRepresentation;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.training.constant.ApiPath;
import com.training.dto.UserDto;
import com.training.service.KeycloakUserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(ApiPath.UserCtrl.CTRL)
@RequiredArgsConstructor
public class UserController {
	
	private final KeycloakUserService keycloakUserService;
	private final ModelMapper modelMapper;
	
	@RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<UserDto>> getUsersBySearchParam(@RequestParam String search){
		List<UserRepresentation> users = keycloakUserService.findByUsername(search);
		List<UserDto> usersDto = Arrays.asList(modelMapper.map(users, UserDto[].class));
        return ResponseEntity.ok(usersDto);
    }
	
	@RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public ResponseEntity<UserDto> getUserByUserId(@PathVariable String userId){
		UserRepresentation userRepresentation = keycloakUserService.findByUserId(userId);
		UserDto userDto = modelMapper.map(userRepresentation, UserDto.class);
        return ResponseEntity.ok(userDto);
    }

}
