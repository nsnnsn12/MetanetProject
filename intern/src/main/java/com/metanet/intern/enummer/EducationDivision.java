package com.metanet.intern.enummer;

public enum EducationDivision {
	major_deep("전공심화"), 
	major_core("전공핵심"), 
	major_required("전공필수"), 
	elective_required("교양필수"), 
	elective_selection("교양선택");
	
	private final String text;
	
	EducationDivision(final String text){
		this.text = text;
	}
	
	public String toString() {
		return text;
	}
}
