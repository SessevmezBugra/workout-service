package com.workout.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.workout.enums.UserRole;
import com.workout.enums.UserStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "GYM_USER")
public class GymUserEntity extends Auditable<String> implements Serializable {
	
private static final long serialVersionUID = 1L;
	
	@Id
    @Column(name="ID")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "GYM_ID")
	private GymEntity gym;
	
	@Column(name="USER_ID")
	private String userId;
	
	@Column(name="USERNAME")
	private String username;
	
	@Enumerated(EnumType.STRING)
	@Column(name="ROLE")
	private UserRole role;
	
	@Enumerated(EnumType.STRING)
	@Column(name="STATUS")
	private UserStatus status;
	

}
