package com.metanet.intern.domain;

import java.util.Date;

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
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

import com.metanet.intern.enummer.Academic;

import groovy.transform.builder.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Student extends BaseEntity{
	//기본 키
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="student_id")
	private Long id;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name= "photo_id")
	private PhotoFile photo;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name= "major_id")
	private Major major;
	
	private String name;
	
	@Temporal(TemporalType.DATE)
	private Date birth;
	
	//학번
	@Column(unique = true)
	private Integer studentNumber;
	
	private String password;
	
	//학기
	private Integer semester;
	
	@Enumerated(EnumType.STRING)
	private Academic status;
	
	private String telNo;

	@Transient
	private MultipartFile image;
	
	@Transient
	private Long majorId;
	
}
