package com.metanet.intern.enummer;

public enum Academic {
	graduated("졸업"), leave("휴학"), attending("재학"), returned("복학");
	
	private final String text;
	
	Academic(final String text){
		this.text = text;
	}
	
	public String toString() {
		return text;
	}
}
