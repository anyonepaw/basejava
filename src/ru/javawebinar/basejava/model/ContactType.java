package ru.javawebinar.basejava.model;

public enum ContactType {

	TELEPHONE("Teл."),
	SKYPE("Skype"),
	MAIL("mail"),
	LINKEDIN("Профиль LinkedIn"),
	GITHUB("Профиль GitHub"),
	STATCKOVERFLOW("Профиль Stackoverflow"),
	HOME_PAGE("Домашняя страница");

	private String title;

	ContactType(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}


}
