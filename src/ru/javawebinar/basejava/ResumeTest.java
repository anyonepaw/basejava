package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.*;


import java.util.*;

public class ResumeTest {

	public static void main(String[] args) {
		Resume resume = new Resume("uuid1", "Григорий Кислин");

		EnumMap<ContactType, String> contacts = resume.getContacts();

		contacts.put(ContactType.TELEPHONE, "+7(921) 855-0482");
		contacts.put(ContactType.SKYPE, "grigory.kislin");
		contacts.put(ContactType.MAIL, "gkislin@yandex.ru");

		EnumMap<SectionType, Text> sections = resume.getSections();

		sections.put(SectionType.PERSONAL,new PlainText("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры."));
		sections.put(SectionType.OBJECTIVE,new PlainText("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"));

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

		sections.put(SectionType.EXPERIENCE, new StringListText(new ArrayList<>(Arrays.asList(
				new OrganizationDescText("Java Online Projects",
				"10/2013 - Сейчас", "Автор проекта. Создание, организация и проведение Java онлайн проектов и стажировок.").toString()))));

		sections.put(SectionType.EDUCATION, new StringListText(new ArrayList<>(Arrays.asList(
				new OrganizationDescText("Coursera", "03/2013 - 05/2013", "\"Functional Programming Principles in Scala\" by Martin Odersky").toString(),
				new OrganizationDescText("Luxoft", "03/2011 - 04/2011", "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"").toString()))));


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
