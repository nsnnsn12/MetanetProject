package com.metanet.intern.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
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
public class Professor extends Manager{
	@ManyToOne
	@JoinColumn(name= "major_id")
	private Major major;
}
