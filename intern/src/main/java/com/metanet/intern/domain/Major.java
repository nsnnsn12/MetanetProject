package com.metanet.intern.domain;
import java.util.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import groovy.transform.builder.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@Entity
@ToString
public class Major extends BaseEntity{
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="major_id")
	private Long id;
	
	private String title;
	@Column(unique = true)
	private String code;
	
//	@OneToMany(mappedBy = "major")
//	private List<Student> students = new ArrayList<>();
//	
//	@OneToMany(mappedBy = "major")
//	private List<Manager> professors = new ArrayList<>();
	
}
