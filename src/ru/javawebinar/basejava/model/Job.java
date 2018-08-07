package ru.javawebinar.basejava.model;

import ru.javawebinar.basejava.util.DateUtil;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Job {

	private final LocalDate fromDate;
	private final LocalDate toDate;
	private final String title;
	private final String description;

	public Job(int startYear, Month startMonth, int endYear, Month endMonth, String title, String description) {
		Objects.requireNonNull(startMonth, "startMonth must not be null");
		Objects.requireNonNull(endMonth, "toDate must not be null");
		Objects.requireNonNull(title, "title must not be null");

		this.fromDate = DateUtil.of(startYear, startMonth);
		this.toDate = DateUtil.of(endYear, endMonth);
		this.title = title;
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

	@Override
	public String toString() {
		DateTimeFormatter df = DateTimeFormatter.ofPattern("MM/YYYY");
		return   fromDate.format(df) +
				" - " + toDate.format(df) +
				":  " + title + '\n' +
				description;
	}
}
