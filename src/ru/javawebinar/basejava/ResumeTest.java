package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.*;


import java.util.Map;

public class ResumeTest {

	public static void main(String[] args) {
		Resume resume = new Resume("uuid1", "Григорий Кислин");

		Map<String, String> contacts = resume.getContacts();
		contacts.put("Тел.", "+7(921) 855-0482");
		contacts.put("Skype", "grigory.kislin");
		contacts.put("Почта", "gkislin@yandex.ru");

		Map<SectionType, Text> sections = resume.getSections();

		Text personal = new PlainText();
		personal.addContent(
				null, "Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.");
		sections.put(SectionType.valueOf("PERSONAL"), personal);

		Text objective = new PlainText();
		objective.addContent(
				null, "Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям");
		sections.put(SectionType.valueOf("OBJECTIVE"), objective);


		Text achievement = new BulletedText();
		achievement.addContent(null, "С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX).\"" +
						"Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA). Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.",
				"Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.",
				"Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. " +
						"Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery." +
						" Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.");
		sections.put(SectionType.valueOf("ACHIEVEMENT"), achievement);

		Text qualifications = new BulletedText();
		qualifications.addContent(null, "JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2",
				"Version control: Subversion, Git, Mercury, ClearCase, Perforce",
				"DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle",
				"MySQL, SQLite, MS SQL, HSQLDB");
		sections.put(SectionType.valueOf("QUALIFICATIONS"), qualifications);

		Text experience = new EnumeratedText();
		experience.addContent("Java Online Projects", "10/2013 - Сейчас", "Автор проекта. Создание, организация и проведение Java онлайн проектов и стажировок.");
		experience.addContent("Wrike", "10/2014 - 01/2016", "Старший разработчик (backend). " +
				"Проектирование и разработка онлайн платформы управления проектами Wrike " +
				"(Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). " +
				"Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.");
		sections.put(SectionType.valueOf("EXPERIENCE"), experience);

		Text education = new EnumeratedText();
		education.addContent("Coursera", "03/2013 - 05/2013", "\"Functional Programming Principles in Scala\" by Martin Odersky");
		education.addContent("Luxoft", "03/2011 - 04/2011", "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"");
		sections.put(SectionType.valueOf("EDUCATION"), education);


		System.out.println(resume.getFullName());
		for (Map.Entry<String, String> contact : contacts.entrySet()) {
			System.out.println(contact.getKey() + ": " + contact.getValue());
		}
		System.out.println("\n");
		for (Map.Entry<SectionType, Text> section : sections.entrySet()) {
			System.out.println(section.getKey().getTitle());
			System.out.println(section.getValue().toString());
		}

		System.out.println(contacts.get("Тел."));
		System.out.println(sections.get(SectionType.valueOf("PERSONAL")));

	}

}
