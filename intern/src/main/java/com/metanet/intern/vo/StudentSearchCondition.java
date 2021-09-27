package com.metanet.intern.vo;

import com.metanet.intern.domain.Major;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper=true)
public class StudentSearchCondition extends SearchCondition{
	//전공 필터
	private Major majorFilter;
}
