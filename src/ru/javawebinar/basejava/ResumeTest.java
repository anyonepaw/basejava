package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.*;


import java.time.LocalDate;
import java.time.Month;
import java.util.*;

public class ResumeTest {

	public static void main(String[] args) {
		Resume resume = new Resume("uuid1", "Григорий Кислин");

		EnumMap<ContactType, String> contacts = resume.getContacts();

		contacts.put(ContactType.TELEPHONE, "+7(921) 855-0482");
		contacts.put(ContactType.SKYPE, "grigory.kislin");
		contacts.put(ContactType.MAIL, "gkislin@yandex.ru");

		EnumMap<SectionType, Text> sections = resume.getSections();

		sections.put(SectionType.PERSONAL, new PlainText("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры."));
		sections.put(SectionType.OBJECTIVE, new PlainText("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"));

		sections.put(SectionType.ACHIEVEMENT, new StringListText(new ArrayList<>(Arrays.asList(
				"С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX).\"" +
						"\"Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA). Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.\"",
				"Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.",
				"Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP."))));

		sections.put(SectionType.QUALIFICATIONS, new StringListText(new ArrayList<>(Arrays.asList(
				"JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2",
				"Version control: Subversion, Git, Mercury, ClearCase, Perforce",
				"DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle",
				"MySQL, SQLite, MS SQL, HSQLDB"))));

		List<Organization> organizationWorkList = new ArrayList<>();
		List<Job> jobs = new ArrayList<>();
		jobs.add(new Job(2012, Month.OCTOBER, 2018, Month.JANUARY,
				"Автор проекта.", "Создание, организация и проведение Java онлайн проектов и стажировок."));
		organizationWorkList.add(new Organization("Java Online Projects", "", jobs));

		sections.put(SectionType.EXPERIENCE, new OrganizationText(organizationWorkList));

		List<Organization> organizationStudyList = new ArrayList<>();
		List<Job> jobs1 = new ArrayList<>();
		jobs1.add(new Job(2011, Month.MARCH,2011, Month.APRIL, "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"", ""));
		organizationStudyList.add(new Organization("Luxoft", "", jobs1));
		List<Job> jobs2 = new ArrayList<>();
		jobs2.add(new Job(2013,  Month.MARCH, 2013,  Month.MAY, "\"Functional Programming Principles in Scala\" by Martin Odersky", ""));
		organizationStudyList.add(new Organization("Coursera", "", jobs2));
		List<Job> jobs3 = new ArrayList<>();
		jobs3.add(new Job(1987,  Month.SEPTEMBER, 1993,  Month.JULY, "Инженер (программист Fortran, C)", ""));
		jobs3.add(new Job(1993,  Month.SEPTEMBER, 1996,  Month.JULY, "Аспирантура (программист С, С++)", ""));
		organizationStudyList.add(new Organization("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики", "", jobs3));
		sections.put(SectionType.EDUCATION, new OrganizationText(organizationStudyList));

		resume.setContacts(contacts);
		resume.setSections(sections);

		System.out.println(resume.getFullName());
		for (EnumMap.Entry<ContactType, String> contact : contacts.entrySet()) {
			System.out.println(contact.getKey().getTitle() + ": " + contact.getValue());
		}
		System.out.println();
		for (Map.Entry<SectionType, Text> section : sections.entrySet()) {
			System.out.println();
			System.out.println(section.getKey().getTitle());
			System.out.println(section.getValue().toString());
		}

		System.out.println();
		System.out.println(sections.get(SectionType.valueOf("PERSONAL")));

	}

}
