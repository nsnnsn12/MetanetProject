package com.metanet.intern.vo;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import com.metanet.intern.enummer.Role;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper=true)
public class ManagerSearchCondition extends SearchCondition{
	//권한필터
	private Role roleFilter;
	//승인여부 필터
	private Integer acceptFilter;
}
