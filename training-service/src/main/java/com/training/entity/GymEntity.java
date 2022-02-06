package com.training.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.training.enums.GymStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "GYM")
public class GymEntity extends Auditable<String> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
    @Column(name="ID")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
	
	@Column(name="NAME")
    private String name;
	
	@Enumerated(EnumType.STRING)
	@Column(name="STATUS")
	private GymStatus status;
	
	@JsonManagedReference
	@OneToMany(mappedBy = "gym", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	private List<GymUserEntity> users = new ArrayList<>();

}
