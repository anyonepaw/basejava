package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.*;


import java.time.Month;
import java.util.*;

public class ResumeTest {

	public static void main(String[] args) {
		Resume resume = new Resume("uuid1", "Григорий Кислин");

		resume.addContact(ContactType.TELEPHONE, "+7(921) 855-0482");
		resume.addContact(ContactType.SKYPE, "grigory.kislin");
		resume.addContact(ContactType.MAIL, "gkislin@yandex.ru");
		resume.addSection(SectionType.PERSONAL, new PlainText("Objective1"));
		resume.addSection(SectionType.OBJECTIVE, new PlainText("Personal data"));
		resume.addSection(SectionType.ACHIEVEMENT, new StringListText("Achievement11", "Achievement12", "Achievement13"));
		resume.addSection(SectionType.QUALIFICATIONS, new StringListText("Java", "SQL", "JavaScript"));
		resume.addSection(SectionType.EXPERIENCE, new OrganizationText(
				new Organization("Organization11", "http://Organization11.ru",
						new Organization.Job(2005, Month.JANUARY, "position1", "content1"), //NOW
						new Organization.Job(2001, Month.MARCH, 2005, Month.JANUARY, "position2", "content2"))
		));
		resume.addSection(SectionType.EDUCATION,
				new OrganizationText(
						new Organization("Institute", null,
								new Organization.Job(1996, Month.JANUARY, 2000, Month.DECEMBER, "aspirant", null),
								new Organization.Job(2001, Month.MARCH, 2005, Month.JANUARY, "student", "IT faculty")),
						new Organization("Organization12", "http://Organization12.ru")));


		System.out.println(resume.getFullName());
		for (EnumMap.Entry<ContactType, String> contact : resume.getContacts().entrySet())
			System.out.println(contact.getKey().getTitle() + ": " + contact.getValue());
		System.out.println();


		for (EnumMap.Entry<SectionType, Text> section : resume.getSections().entrySet()) {
			System.out.println();
			System.out.println(section.getKey().getTitle());
			System.out.println(section.getValue().toString());
		}

		System.out.println();
		System.out.println(resume.getSection(SectionType.valueOf("PERSONAL")));

	}

}

