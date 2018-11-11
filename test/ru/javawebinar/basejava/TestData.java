package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.*;

import java.util.Collections;
import java.util.UUID;

public class TestData {


	public static final String UUID1 = UUID.randomUUID().toString();
	public static final String UUID2 = UUID.randomUUID().toString();
	public static final String UUID3 = UUID.randomUUID().toString();
	public static final String DUMMY = "DUMMY";
	public static final String FULL_NAME1 = "Григорий Кислин";
	public static final String FULL_NAME2 = "Василий Китовский";
	public static final String FULL_NAME3 = "Name3";
	public static final Resume R1;
	public static final Resume R2;
	public static final Resume R3;

	static {

		R1 = new Resume(UUID1, FULL_NAME1);
		R2 = new Resume(UUID2, FULL_NAME2);
		R3 = new Resume(UUID3, FULL_NAME3);

		R1.addContact(ContactType.MAIL, "mail1@ya.ru");
		R1.addContact(ContactType.TELEPHONE, "11111");
		R1.addSection(SectionType.OBJECTIVE, new PlainText("Objective1"));
		R1.addSection(SectionType.PERSONAL, new PlainText("Personal data"));
		R1.addSection(SectionType.ACHIEVEMENT, new StringListText("Achievement11", "Achievement12", "Achievement13"));
		R1.addSection(SectionType.QUALIFICATIONS, new StringListText(Collections.emptyList()));
//		R1.addSection(SectionType.EDUCATION,
//				new OrganizationText(
//						new Organization("Institute", "",
//								new Organization.Job(1996, Month.JANUARY, 2000, Month.DECEMBER, "aspirant", null),
//								new Organization.Job(2001, Month.MARCH, 2005, Month.JANUARY, "student", "IT faculty")),
//						new Organization("Organization12", "http://Organization12.ru")));
		R2.addContact(ContactType.SKYPE, "skype2");
		R2.addContact(ContactType.TELEPHONE, "22222");
		R2.addSection(SectionType.OBJECTIVE, new PlainText("Objective1"));
		R2.addSection(SectionType.QUALIFICATIONS, new StringListText("Q1", "Q2", "Q3"));
////		R1.addSection(SectionType.EXPERIENCE,
//				new OrganizationText(
//						new Organization("Organization2", "http://Organization2.ru",
//								new Organization.Job(2015, Month.JANUARY, 2017, Month.JANUARY,"position1", "content1"))));
	}
}
