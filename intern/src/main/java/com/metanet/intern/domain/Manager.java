package com.metanet.intern.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.metanet.intern.enummer.Role;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn
@Getter
@NoArgsConstructor
@Setter
@Entity
@ToString
public class Manager extends BaseEntity{

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="manager_id")
	private Long id;
	
	@OneToOne
	@JoinColumn(name= "photo_id")
	private PhotoFile photo_id;
	
	@Column(unique = true)
	@NotNull
	@NotBlank
	private String loginId;
	
	@NotNull
	@NotBlank
	private String password;
	
	@NotNull
	@NotBlank
	private String name;
	
	@Enumerated(EnumType.STRING)
	@NotNull
	private Role role;
	
	@Temporal(TemporalType.DATE)
	private Date birth;
	
	private Integer isAccept = 0;
	private String telNo;
	
	
}
