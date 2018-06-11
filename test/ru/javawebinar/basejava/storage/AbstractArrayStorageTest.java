package ru.javawebinar.basejava.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import static org.junit.Assert.assertEquals;


public abstract class AbstractArrayStorageTest {

	private Storage storage;
	protected static final Resume resume1 = new Resume("uuid1");
	protected static final Resume resume2 = new Resume("uuid2");
	protected static final Resume resume3 = new Resume("uuid3");

	public AbstractArrayStorageTest(Storage storage) {
		this.storage = storage;
	}

	@Before
	public void setUp() {
		storage.clear();
		storage.save(resume1);
		storage.save(resume2);
	}

	@Test
	public void clear() {
		storage.clear();
		assertEquals(0, storage.size());
	}

	@Test
	public void update() {
		storage.update(resume2);
		assertEquals(resume2, storage.get(resume2.getUuid()));
	}

	@Test(expected = NotExistStorageException.class)
	public void updateNotExist() {
		storage.get("dummy");
	}

	@Test
	public void save() {
		storage.save(resume3);
		assertEquals(3, storage.size());
	}

	@Test(expected = ExistStorageException.class)
	public void saveAlreadyExist() throws Exception {
		storage.save(resume1);
	}

	@Test(expected = StorageException.class)
	public void saveStorageIsFull() throws Exception {
		for (int i = 2; i < 10000; i++) {
			storage.save(new Resume("uuid" + (i + 1)));
		}
		storage.save(new Resume("uuid10000"));
	}

	@Test
	public void delete() {
		storage.delete("uuid1");
		assertEquals(1, storage.size());
	}

	@Test(expected = NotExistStorageException.class)
	public void deleteNotExist() {
		storage.get("dummy");
	}

	@Test
	public void get() {
		assertEquals(resume2, storage.get(resume2.getUuid()));
	}

	@Test(expected = NotExistStorageException.class)
	public void getNotExist() {
		storage.get("dummy");
	}

	@Test
	public void getAll() {
		Resume[] resumes = new Resume[]{resume1, resume2};
		Assert.assertArrayEquals(resumes, storage.getAll());
	}

	@Test
	public void size() {
		Resume[] resumes = new Resume[]{resume1, resume2};
		assertEquals(resumes.length, storage.size());
	}

}