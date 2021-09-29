package com.metanet.intern.domain;
import java.util.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import groovy.transform.builder.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Reply extends BaseEntity{
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="reply_id")
	private Long id;
	
	@Lob
	private String contents;
	
	@ManyToOne
	@JsonManagedReference
	@JoinColumn(name="notice_id")
	private Notice notice;
	
	@ManyToOne
	@JsonManagedReference
	@JoinColumn(name="manager_id")
	private Manager manager;
	
	public void setNotice(Notice notice) {
		this.notice = notice;
		notice.getReplies().add(this);
	}
	
}
