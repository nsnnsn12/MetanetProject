package com.metanet.intern.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.metanet.intern.enummer.Role;

import groovy.transform.builder.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn
@Getter
@NoArgsConstructor
@Builder
@Entity
public class Manager extends BaseEntity{

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="manager_id")
	private Long id;
	
	@OneToOne
	@JoinColumn(name= "photo_id")
	private PhotoFile photo_id;
	
	@Column(unique = true)
	private String loginId;
	private String password;
	private String name;
	private Role role;
	@Temporal(TemporalType.DATE)
	private Date birth;
	private Integer isAccept;
	private String telNo;
	
	
}
