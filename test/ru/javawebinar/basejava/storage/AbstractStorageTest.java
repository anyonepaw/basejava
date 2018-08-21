package ru.javawebinar.basejava.storage;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.*;

import java.io.File;
import java.time.Month;
import java.util.*;


public abstract class AbstractStorageTest {

	protected static final File STORAGE_DIR = new File("/Users/Angelika/Documents/Projects/basejava/src/ru/javawebinar/basejava/storage/resumes");
	protected Storage storage;


	private static final String UUID1 = "uuid1";
	private static final String UUID2 = "uuid2";
	private static final String UUID3 = "uuid3";
	private static final String DUMMY = "DUMMY";
	private static final String FULL_NAME1 = "Григорий Кислин";
	private static final String FULL_NAME2 = "Василий Китовский";
	private static final String FULL_NAME3 = "Name3";
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
		R1.addContact(ContactType.MAIL, "mail1@ya.ru");
		R1.addContact(ContactType.TELEPHONE, "11111");
		R1.addSection(SectionType.OBJECTIVE, new PlainText("Objective1"));
		R1.addSection(SectionType.PERSONAL, new PlainText("Personal data"));
		R1.addSection(SectionType.ACHIEVEMENT, new StringListText("Achivment11", "Achivment12", "Achivment13"));
		R1.addSection(SectionType.QUALIFICATIONS, new StringListText(Collections.emptyList()));

		R1.addSection(SectionType.EDUCATION,
				new OrganizationText(
						new Organization("Institute", "",
								new Organization.Job(1996, Month.JANUARY, 2000, Month.DECEMBER, "aspirant", null),
								new Organization.Job(2001, Month.MARCH, 2005, Month.JANUARY, "student", "IT faculty")),
						new Organization("Organization12", "http://Organization12.ru")));
		R2.addContact(ContactType.SKYPE, "skype2");
		R2.addContact(ContactType.TELEPHONE, "22222");
		R1.addSection(SectionType.EXPERIENCE,
				new OrganizationText(
						new Organization("Organization2", "http://Organization2.ru",
								new Organization.Job(2015, Month.JANUARY, 2017, Month.JANUARY,"position1", "content1"))));
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