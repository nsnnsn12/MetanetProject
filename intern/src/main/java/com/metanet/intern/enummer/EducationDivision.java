package com.metanet.intern.enummer;

public enum EducationDivision {
	MI("전공심화"), MC("전공핵심"), MN("전공필수"), ELN("교양필수"), ELS("교양선택");

	private final String text;
	
	EducationDivision(final String text){
		this.text = text;
	}
	
	public String toString() {
		return text;
	}
}
