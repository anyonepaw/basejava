package ru.javawebinar.basejava.model;

public class OrganizationDescText extends Text {

	String title;
	String date;
	String description;


	public OrganizationDescText(String title, String date, String description) {
		this.title = title;
		this.date = date;
		this.description = description;
	}

	public String toString() {
		return title + "\n" + date + "  " + description;
	}
}
