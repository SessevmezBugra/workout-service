package com.training.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GymStatus {
	ACTIVE("Aktif"),
	DELETED("Silindi");
	
	@JsonValue
	private String status;
}
