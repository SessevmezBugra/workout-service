package com.workout.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "WORKOUT")
public class WorkoutEntity extends Auditable<String> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "NAME")
	private String name;
	
	@Column(name = "DESCRIPTION")
	private String desc;
	
	@Column(name="USERNAME")
	private String username;
	
	@Column(name="USER_ID")
	private String userId;

	@JsonManagedReference
	@OneToMany(mappedBy = "workout", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	private List<WorkoutSectionEntity> sections = new ArrayList<>();
}
