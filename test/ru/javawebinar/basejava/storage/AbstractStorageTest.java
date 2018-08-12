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
	private static final Resume R1;
	private static final Resume R2;
	private static final Resume R3;

	public AbstractStorageTest(Storage storage) {
		this.storage = storage;
	}

	static {

		R1 = new Resume(UUID1, FULL_NAME1);
		R2 = new Resume(UUID2, FULL_NAME2);
		R3 = new Resume(UUID3, FULL_NAME3);


		R1.addContact(ContactType.TELEPHONE, "+7(921) 855-0482");
		R1.addContact(ContactType.SKYPE, "grigory.kislin");
		R1.addContact(ContactType.MAIL, "gkislin@yandex.ru");
		R1.addSection(SectionType.PERSONAL, new PlainText("Objective1"));
		R1.addSection(SectionType.OBJECTIVE, new PlainText("Personal data"));
		R1.addSection(SectionType.ACHIEVEMENT, new StringListText("Achievement11", "Achievement12", "Achievement13"));
		R1.addSection(SectionType.QUALIFICATIONS, new StringListText("Java", "SQL", "JavaScript"));
		R1.addSection(SectionType.EXPERIENCE, new OrganizationText(
				new Organization("Organization11", "http://Organization11.ru",
						new Organization.Job(2005, Month.JANUARY, "position1", "content1"), //NOW
						new Organization.Job(2001, Month.MARCH, 2005, Month.JANUARY, "position2", "content2"))
		));
		R1.addSection(SectionType.EDUCATION,
				new OrganizationText(
						new Organization("Institute", null,
								new Organization.Job(1996, Month.JANUARY, 2000, Month.DECEMBER, "aspirant", null),
								new Organization.Job(2001, Month.MARCH, 2005, Month.JANUARY, "student", "IT faculty")),
						new Organization("Organization12", "http://Organization12.ru")));


		R2.addContact(ContactType.SKYPE, "vasilisk");
		R2.addContact(ContactType.MAIL, "vaskitovsky@gmail.com");
		R2.addContact(ContactType.GITHUB, "https://github.com/kitovsky");
		R2.addSection(SectionType.PERSONAL, new PlainText("Порядочность, ответственность, способность работать в команде"));
		R2.addSection(SectionType.OBJECTIVE, new PlainText("Тимлид"));
		R2.addSection(SectionType.ACHIEVEMENT, new StringListText("Более 20+ успешно проведенных проектов"));
		R2.addSection(SectionType.QUALIFICATIONS, new StringListText("JEE ", "Tomcat", "Jetty", "WebLogic", " WSO2"));
		R2.addSection(SectionType.EXPERIENCE, new OrganizationText(new Organization("Java Online Projects", "",
				new Organization.Job(2005, Month.NOVEMBER, 2010, Month.JULY, "Разработчик, Java)", ""),
				new Organization.Job(2010, Month.SEPTEMBER, 2018, Month.JULY, "Тимлид, Разработчик, Java)", "")),
				new Organization("Coursera", "",
						new Organization.Job(2007, Month.DECEMBER, 2008, Month.MAY, "\"Algorithms and Data Structures", ""))));
		R2.addSection(SectionType.EDUCATION,
				new OrganizationText(
						new Organization("University", null,
								new Organization.Job(1996, Month.JANUARY, 2000, Month.DECEMBER, "aspirant", null),
								new Organization.Job(2001, Month.MARCH, 2005, Month.JANUARY, "student", "IT faculty"))));

		R3.addContact(ContactType.TELEPHONE, "+7(916) 975-0455");
		R3.addContact(ContactType.SKYPE, "skyworker");
		R3.addContact(ContactType.MAIL, "seletsky5675@mail.ru");
		R3.addContact(ContactType.STATCKOVERFLOW, "https://stackoverflow.com/users/profile/skyworker");

		R3.addSection(SectionType.PERSONAL, new PlainText("Аналитический склад ума, упорство и умение работать в команде"));
		R3.addSection(SectionType.OBJECTIVE, new PlainText("Java архитектор"));
		R3.addSection(SectionType.ACHIEVEMENT, new StringListText("Более 50+ успешно проведенных проектов"));
		R3.addSection(SectionType.QUALIFICATIONS, new StringListText("Java", "SQL", "JavaScript"));

		R3.addSection(SectionType.EXPERIENCE, new OrganizationText(new Organization("IT Tech", "", new Organization.Job(2013, Month.NOVEMBER, 2015, Month.JULY, "Разработчик, Java)", ""),
				new Organization.Job(2015, Month.SEPTEMBER, 2018, Month.JULY, "Архитектор, Java)", ""))));

		R3.addSection(SectionType.EDUCATION, new OrganizationText(new Organization("Coursera", "",
				new Organization.Job(2007, Month.DECEMBER, 2008, Month.MAY, "\"Algorithms and Data Structures", ""))));


	}

	@Before
	public void setUp() {
		storage.clear();
		storage.save(R1);
		storage.save(R2);
	}

	@Test
	public void clear() {
		storage.clear();
		assertSize(0);
	}

	@Test
	public void update() {
		storage.update(R2);
		assertEquals(R2, storage.get(UUID2));
	}

	@Test(expected = NotExistStorageException.class)
	public void updateNotExist() {
		storage.get(DUMMY);
	}

	@Test
	public void save() {
		storage.save(R3);
		assertSize(3);
		assertEquals(R3, storage.get(UUID3));
	}

	@Test(expected = ExistStorageException.class)
	public void saveAlreadyExist() {
		storage.save(R1);
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
		assertEquals(R2, storage.get(UUID2));
	}

	@Test(expected = NotExistStorageException.class)
	public void getNotExist() {
		storage.get(DUMMY);
	}

	@Test
	public void getAllSorted() {
		List<Resume> resumes = Arrays.asList(R2, R1);
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