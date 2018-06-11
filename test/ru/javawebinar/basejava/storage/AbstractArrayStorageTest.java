package ru.javawebinar.basejava.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;


public abstract class AbstractArrayStorageTest {

	private Storage storage;
	protected static final Resume resume1 = new Resume("uuid1");
	protected static final Resume resume2 = new Resume("uuid2");
	protected static final Resume resume3 = new Resume("uuid3");

	public AbstractArrayStorageTest(Storage storage) {
		this.storage = storage;
	}

	@Before
	public void setUp() throws Exception {
		storage.clear();
		storage.save(resume1);
		storage.save(resume2);
	}

	@Test
	public void clear() throws Exception {
		storage.clear();
		Assert.assertEquals(0, storage.size());
	}

	@Test
	public void update() throws Exception {
		storage.update(resume2);
	}

	@Test
	public void save() throws Exception {
		storage.save(resume3);
		Assert.assertEquals(3, storage.size());
	}

	@Test(expected = ExistStorageException.class)
	public void saveAlreadyExist() throws Exception {
		storage.save(resume1);
	}

	@Test(expected = StorageException.class)
	public void saveStorageIsFull() throws Exception {
		for (int i = 2; i < 10000; i++) {
			storage.save(new Resume("uuid" + (i+1)));
		}
		storage.save(new Resume("uuid10000"));
	}

	@Test
	public void delete() throws Exception {
		storage.delete("uuid1");
		Assert.assertEquals(1, storage.size());
	}

	@Test
	public void get() throws Exception {
		Assert.assertEquals(resume2, storage.get(resume2.getUuid()));
	}

	@Test(expected = NotExistStorageException.class)
	public void getNotExist() throws Exception {
		storage.get("dummy");
	}

	@Test
	public void getAll() throws Exception {
		Resume[] resumes = new Resume[]{resume1, resume2};
		Assert.assertArrayEquals(resumes, storage.getAll());
	}

	@Test
	public void size() throws Exception {
		Resume[] resumes = new Resume[]{resume1, resume2};
		Assert.assertEquals(resumes.length, storage.size());
	}

}