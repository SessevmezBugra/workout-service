package com.training.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.training.enums.GymStatus;
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
public class GymDto {

	private Long id;

	private String name;

	private GymStatus status;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", locale = "tr-TR")
	private Date createdDate;
	
	private String createdBy;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", locale = "tr-TR")
	private Date updatedOn;
	
	private String updatedBy;
	
	private UserRole userRole;
	
	private UserStatus userStatus;
}
