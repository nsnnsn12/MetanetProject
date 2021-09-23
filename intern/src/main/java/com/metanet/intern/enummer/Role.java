package com.metanet.intern.enummer;

public enum Role {
	
	manager("행정직원"), professor("교수"), admin("관리자");
	
	private final String text;
	
	Role(final String text){
		this.text = text;
	}
	
	public String toString() {
		return text;
	}
}
