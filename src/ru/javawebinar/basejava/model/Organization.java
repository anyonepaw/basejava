package ru.javawebinar.basejava.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Organization {

	private String title;
	private LocalDate fromDate;
	private LocalDate toDate;
	private String description;

	public Organization(String title, LocalDate fromDate, LocalDate toDate, String description) {
		this.title = title;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public LocalDate getFromDate() {
		return fromDate;
	}

	public LocalDate getToDate() {
		return toDate;
	}

	public String getDescription() {
		return description;
	}

	public String toString() {
		DateTimeFormatter dt = DateTimeFormatter.ofPattern("MM/yyyy");
	return title + "\n" + dt.format(fromDate) + " - " +
				(toDate.equals(LocalDate.now())? "Сейчас" : dt.format(toDate) +
				"  " + description);
	}

}
