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
public class UserDto {

	private String id;
	
	private String username;
	
	private String firstName;
	
	private String lastName;
	
	private UserRole role;
	
	private UserStatus status;
	
}
