package com.training.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserStatus {

	INVITED,
	ACTIVE,
	DELETED;
}
