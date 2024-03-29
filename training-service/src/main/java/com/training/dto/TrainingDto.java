package com.training.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

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
public class TrainingDto {

	private Long id;

	private String name;

	private String desc;

	private String username;

	private String userId;

	private String creatorFirstName;

	private String creatorLastName;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", locale = "tr-TR")
	private Date createdDate;

	private String createdBy;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", locale = "tr-TR")
	private Date updatedOn;

	private String updatedBy;
}
