package ru.javawebinar.basejava.storage;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.*;

import java.time.Month;
import java.util.*;


public abstract class AbstractStorageTest {

	protected Storage storage;


	private static final String UUID1 = "uuid1";
	private static final String UUID2 = "uuid2";
	private static final String UUID3 = "uuid3";
	private static final String DUMMY = "DUMMY";
	private static final String FULL_NAME1 = "Григорий Кислин";
	private static final String FULL_NAME2 = "Высилий Китовский";
	private static final String FULL_NAME3 = "Игорь Селецкий";
	private static final Resume RESUME_1;
	private static final Resume RESUME_2;
	private static final Resume RESUME_3;

	public AbstractStorageTest(Storage storage) {
		this.storage = storage;
	}

	static {

		EnumMap<ContactType, String> contacts = new EnumMap<>(ContactType.class);
		contacts.put(ContactType.TELEPHONE, "+7(921) 855-0482");
		contacts.put(ContactType.SKYPE, "grigory.kislin");
		contacts.put(ContactType.MAIL, "gkislin@yandex.ru");
		EnumMap<SectionType, Text> sections = new EnumMap<>(SectionType.class);
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
		List<Job> jobsForWork = new ArrayList<>();
		jobsForWork.add(new Job(2012, Month.OCTOBER, 2018, Month.JANUARY,
				"Автор проекта.", "Создание, организация и проведение Java онлайн проектов и стажировок."));
		organizationWorkList.add(new Organization("Java Online Projects", "", jobsForWork));
		sections.put(SectionType.EXPERIENCE, new OrganizationText(organizationWorkList));
		List<Organization> organizationStudyList = new ArrayList<>();
		List<Job> studies = new ArrayList<>();
		studies.add(new Job(1987,  Month.SEPTEMBER, 1993,  Month.JULY, "Инженер (программист Fortran, C)", ""));
		studies.add(new Job(1993,  Month.SEPTEMBER, 1996,  Month.JULY, "Аспирантура (программист С, С++)", ""));
		organizationStudyList.add(new Organization("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики", "", studies));
		sections.put(SectionType.EDUCATION, new OrganizationText(organizationStudyList));


		EnumMap<ContactType, String> contacts2 = new EnumMap<>(ContactType.class);
		contacts.put(ContactType.SKYPE, "vasilisk");
		contacts.put(ContactType.MAIL, "vaskitovsky@gmail.com");
		contacts.put(ContactType.GITHUB, "https://github.com/kitovsky");
		EnumMap<SectionType, Text> sections2 = new EnumMap<>(SectionType.class);
		sections2.put(SectionType.PERSONAL, new PlainText("Порядочность, ответственность, способность работать в команде"));
		sections2.put(SectionType.OBJECTIVE, new PlainText("Тимлид"));
		sections2.put(SectionType.ACHIEVEMENT, new StringListText(new ArrayList<>(Arrays.asList(
				"Более 20+ успешно проведенных проектов"))));
		sections2.put(SectionType.QUALIFICATIONS, new StringListText(new ArrayList<>(Arrays.asList(
				"JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2"))));
		List<Organization> organizationWorkList2 = new ArrayList<>();
		List<Job> jobsForWork2 = new ArrayList<>();
		jobsForWork2.add(new Job(2005,  Month.NOVEMBER, 2010,  Month.JULY, "Разработчик, Java)", ""));
		jobsForWork2.add(new Job(2010,  Month.SEPTEMBER, 2018,  Month.JULY, "Тимлид, Разработчик, Java)", ""));
		organizationWorkList.add(new Organization("Java Online Projects", "", jobsForWork2));
		sections.put(SectionType.EXPERIENCE, new OrganizationText(organizationWorkList2));
		List<Organization> organizationStudyList2 = new ArrayList<>();
		List<Job> studies2 = new ArrayList<>();
		studies2.add(new Job(2007,  Month.DECEMBER, 2008,  Month.MAY,  "\"Algorithms and Data Structures", ""));
		organizationStudyList.add(new Organization("Coursera", "", studies2));
		sections.put(SectionType.EDUCATION, new OrganizationText(organizationStudyList2));


		EnumMap<ContactType, String> contacts3 = new EnumMap<>(ContactType.class);
		contacts3.put(ContactType.TELEPHONE, "+7(916) 975-0455");
		contacts3.put(ContactType.SKYPE, "skyworker");
		contacts3.put(ContactType.MAIL, "seletsky5675@mail.ru");
		contacts3.put(ContactType.STATCKOVERFLOW, "https://stackoverflow.com/users/profile/skyworker");
		EnumMap<SectionType, Text> sections3 = new EnumMap<>(SectionType.class);
		sections3.put(SectionType.PERSONAL, new PlainText("Аналитический склад ума, упорство и умение работать в команде"));
		sections3.put(SectionType.OBJECTIVE, new PlainText("Java архитектор"));
		sections3.put(SectionType.ACHIEVEMENT, new StringListText(new ArrayList<>(Arrays.asList(
				"Более 50+ успешно проведенных проектов"))));
		sections3.put(SectionType.QUALIFICATIONS, new StringListText(new ArrayList<>(Arrays.asList(
				"JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2"))));
		List<Organization> organizationWorkList3 = new ArrayList<>();
		List<Job> jobsForWork3 = new ArrayList<>();
		jobsForWork3.add(new Job(2013,  Month.NOVEMBER, 2015,  Month.JULY, "Разработчик, Java)", ""));
		jobsForWork3.add(new Job(2015,  Month.SEPTEMBER, 2018,  Month.JULY, "Архитектор, Java)", ""));
		organizationWorkList.add(new Organization("IT Tech", "", jobsForWork3));
		sections.put(SectionType.EXPERIENCE, new OrganizationText(organizationWorkList3));
		List<Organization> organizationStudyList3 = new ArrayList<>();
		List<Job> studies3 = new ArrayList<>();
		studies3.add(new Job(2007,  Month.DECEMBER, 2008,  Month.MAY,  "\"Algorithms and Data Structures", ""));
		organizationStudyList.add(new Organization("Coursera", "", studies3));
		sections.put(SectionType.EDUCATION, new OrganizationText(organizationStudyList3));


		RESUME_1 = new Resume(UUID1, FULL_NAME1, contacts, sections);
		RESUME_2 = new Resume(UUID2, FULL_NAME2, contacts2, sections2);
		RESUME_3 = new Resume(UUID3, FULL_NAME3, contacts3, sections3);
	}

	@Before
	public void setUp() {
		storage.clear();
		storage.save(RESUME_1);
		storage.save(RESUME_2);
	}

	@Test
	public void clear() {
		storage.clear();
		assertSize(0);
	}

	@Test
	public void update() {
		storage.update(RESUME_2);
		assertEquals(RESUME_2, storage.get(UUID2));
	}

	@Test(expected = NotExistStorageException.class)
	public void updateNotExist() {
		storage.get(DUMMY);
	}

	@Test
	public void save() {
		storage.save(RESUME_3);
		assertSize(3);
		assertEquals(RESUME_3, storage.get(UUID3));
	}

	@Test(expected = ExistStorageException.class)
	public void saveAlreadyExist() {
		storage.save(RESUME_1);
	}

	@Test
	public void delete() {
		storage.delete(UUID1);
		assertEquals(1, storage.size());
	}

	@Test(expected = NotExistStorageException.class)
	public void deleteNotExist() {
		storage.get(DUMMY);
	}

	@Test
	public void get() {
		assertEquals(RESUME_2, storage.get(UUID2));
	}

	@Test(expected = NotExistStorageException.class)
	public void getNotExist() {
		storage.get(DUMMY);
	}

	@Test
	public void getAllSorted() {
		List<Resume> resumes = Arrays.asList(RESUME_2, RESUME_1);
		assertEquals(resumes, storage.getAllSorted());
	}

	@Test
	public void size() {
		assertSize(2);
	}

	private void assertSize(int size) {
		assertEquals(size, storage.size());
	}

}