package ru.javawebinar.basejava.model;

public enum ContactType {

	TELEPHONE("Teл."),
	SKYPE("Skype"),
	MAIL("mail");

	private String title;

	ContactType(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}



}
