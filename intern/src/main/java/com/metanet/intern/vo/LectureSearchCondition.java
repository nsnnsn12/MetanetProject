package com.metanet.intern.vo;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import com.metanet.intern.domain.Major;
import com.metanet.intern.enummer.EducationDivision;
import com.metanet.intern.enummer.Role;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper=true)
public class LectureSearchCondition extends SearchCondition{
	private Major major;
	private Integer credit;
	private EducationDivision division;
}
