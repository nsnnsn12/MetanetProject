package com.metanet.intern.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import groovy.transform.builder.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
@Entity
public class Lecture extends BaseEntity{
	//강의
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="lecture_id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="manager_id")
	private Manager professor;
	
	@OneToOne
	@JoinColumn(name="lecture_plan_id")
	private LecturePlanFile lecturePlanFile;
	
	@ManyToOne
	@JoinColumn(name="education_id")
	private Education education;
	private Integer maximumPerson;
	private Integer totalHour;
	private Integer weekHour;
	private String email;
	private Integer lectureProcCount;
	
}
