package com.metanet.intern.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.metanet.intern.enummer.Academic;

import groovy.transform.builder.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
@Entity
public class StudentLecture extends BaseEntity{
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="student_lecture_id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="student_id")
	private Student student;
	
	@ManyToOne
	@JoinColumn(name="lecture_id")
	private Lecture lecture;
	private Integer finalScore;
	private Integer middleScore;
	private Integer reportScore;
	private Integer attendanceScore;
	
}
