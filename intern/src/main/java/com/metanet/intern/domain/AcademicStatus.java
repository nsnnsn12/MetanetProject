package com.metanet.intern.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.metanet.intern.enummer.Academic;

import groovy.transform.builder.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
@Entity
public class AcademicStatus extends BaseEntity{
	//학적상태이력
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="academic_status_id")
	private Long id;
	
	@Enumerated(EnumType.STRING)
	private Academic status;
}
