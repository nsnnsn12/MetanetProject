package com.metanet.intern.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import com.metanet.intern.enummer.EducationDivision;

import groovy.transform.builder.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Builder
@Entity
public class Education extends BaseEntity{
	//교과목
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="education_id")
	private Long id;
	private String title;
	@ManyToOne
	@JoinColumn(name="major_id")
	private Major major;
	
	@Enumerated(EnumType.STRING)
	private EducationDivision division;
	
	@OneToMany(mappedBy = "education")
	private List<Lecture> lectures;
	
	@Column(unique = true)
	private String code;
	private Integer credit;
	@Lob
	private String content;
	
	@Transient
	private List<Manager> professors;
}
