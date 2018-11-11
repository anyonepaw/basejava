package ru.javawebinar.basejava.util;

import ru.javawebinar.basejava.model.Organization;

public class ToHtml {
	public static boolean isEmpty(String str) {
		return str == null || str.trim().length() == 0;
	}

	public static String formatDates(Organization.Job job) {
		return DateUtil.format(job.getFromDate()) + " - " + DateUtil.format(job.getToDate());
	}

}

