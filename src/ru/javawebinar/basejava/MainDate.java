package ru.javawebinar.basejava;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class MainDate {

	public static void main(String[] args) throws IOException {
		long start = System.currentTimeMillis();
		Date date = new Date();
		System.out.println(System.currentTimeMillis() - start);
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));
		System.out.println(calendar.getTime());

		LocalDate ld = LocalDate.now();
		LocalTime lt = LocalTime.now();
		LocalDateTime localDateTime = LocalDateTime.of(ld, lt);
		System.out.println(localDateTime);

		SimpleDateFormat sdf = new SimpleDateFormat("YY/MM/dd");
		System.out.println(sdf.format(date));

		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("YY/MM/dd");
		System.out.println(dateTimeFormatter.format(localDateTime));


		BufferedReader br = new BufferedReader (new InputStreamReader(System.in));
		System.out.println(Integer.parseInt(br.readLine()) + Integer.parseInt(br.readLine()));
	}

}
