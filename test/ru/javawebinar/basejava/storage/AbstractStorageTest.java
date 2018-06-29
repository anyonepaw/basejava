package ru.javawebinar.basejava.storage;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;
import java.util.List;



public abstract class AbstractStorageTest {

	protected Storage storage;

	private static final String UUID1 = "uuid1";
	private static final String UUID2 = "uuid2";
	private static final String UUID3 = "uuid3";
	private static final String DUMMY = "DUMMY";
	private static final String FULL_NAME1 = "FULL_NAME1";
	private static final String FULL_NAME2 = DUMMY + "_NAME2";
	private static final String FULL_NAME3 = "FULL_NAME1";
	private static final Resume RESUME_1;
	private static final Resume RESUME_2;
	private static final Resume RESUME_3;

	public AbstractStorageTest(Storage storage) {
		this.storage = storage;
	}

	static {
		RESUME_1 = new Resume(UUID1, FULL_NAME1);
		RESUME_2 = new Resume(UUID2, FULL_NAME2);
		RESUME_3 = new Resume(UUID3, FULL_NAME3);
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