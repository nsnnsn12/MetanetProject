package com.metanet.intern.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import java.util.*;

import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.metanet.intern.enummer.AttendanceStatus;

import groovy.transform.builder.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
@Entity
public class Attendance {
	//출석
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="attendance_id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="student_lecture_id")
	private StudentLecture lecture;
	
	@Enumerated(EnumType.STRING)
	private AttendanceStatus status;
	
	@Temporal(TemporalType.DATE)
	private Date checkDate;
	
}
