package ru.javawebinar.basejava.storage;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;



public abstract class AbstractArrayStorageTest {

	private Storage storage;
	protected static final String UUID1 = "uuid1";
	protected static final String UUID2 = "uuid2";
	protected static final String UUID3 = "uuid3";
	protected static final String DUMMY = "dummy";
	protected static final Resume RESUME_1 = new Resume(UUID1);
	protected static final Resume RESUME_2 = new Resume(UUID2);
	protected static final Resume RESUME_3 = new Resume(UUID3);

	public AbstractArrayStorageTest(Storage storage) {
		this.storage = storage;
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
		assertEquals(0, storage.size());
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
		assertEquals(3, storage.size());
	}

	@Test(expected = ExistStorageException.class)
	public void saveAlreadyExist() {
		storage.save(RESUME_1);
	}

	@Test(expected = StorageException.class)
	public void saveStorageIsFull() {
		for (int i = 2; i < 10000; i++) {
			storage.save(new Resume());
		}
		storage.save(new Resume());
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
	public void getAll() {
		Resume[] resumes = new Resume[]{RESUME_1, RESUME_2};
		assertArrayEquals(resumes, storage.getAll());
	}

	@Test
	public void size() {
		Resume[] resumes = new Resume[]{RESUME_1, RESUME_2};
		assertEquals(resumes.length, storage.size());
	}

}