package com.metanet.intern.vo;

import org.springframework.stereotype.Component;

import com.metanet.intern.enummer.Role;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SearchCondition {
	//검색어
	private String text ="";
	//검색 조건
	private String type ="";
}
