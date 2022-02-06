package com.training.dto;

import com.training.enums.UserRole;
import com.training.enums.UserStatus;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class GymUserDto {

	private Long id;

	private String userId;
	
	private String username;
	
	private UserRole role;
	
	private UserStatus status;
	
	private String firstName;
	
	private String lastName;
}
