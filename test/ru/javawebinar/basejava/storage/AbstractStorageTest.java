package ru.javawebinar.basejava.storage;

import static org.junit.Assert.*;
import static ru.javawebinar.basejava.TestData.*;


import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.Config;

import java.io.File;
import java.util.*;


public abstract class AbstractStorageTest {

	protected static final File STORAGE_DIR = Config.get().getStorageDir();
	protected Storage storage;


	public AbstractStorageTest(Storage storage) {
		this.storage = storage;
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
		assertEquals(R1, storage.get(UUID1));
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