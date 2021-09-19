package com.metanet.intern.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import groovy.transform.builder.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
@Entity
public class LecturePlanFile extends BaseEntity{
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="lecture_plan_id")
	private Long id;
	
	private String originalFileName;
	private String saveFileName;
	private String filePath;
	private Integer fileSize;
	private String mimeType;
}
