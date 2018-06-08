package ru.javawebinar.basejava.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;


public abstract class AbstractArrayStorageTest {

	private  Storage storage = new ArrayStorage();
	private static final String UUID_1 = "uuid1";
	private static final String UUID_2 = "uuid2";
	private static final String UUID_3 = "uuid3";
	private static final Resume RESUME_FOR_CHECKING = new Resume("uuid4");

	@Before
	public void setUp() throws Exception {
		storage.clear();
		storage.save(new Resume(UUID_1));
		storage.save(new Resume(UUID_2));
		storage.save(new Resume(UUID_3));
		storage.save(RESUME_FOR_CHECKING);
	}

	@Test
	public void clear() {
		storage.clear();
		Assert.assertEquals(0, storage.size());
	}

	@Test
	public void update() throws Exception {
		storage.update(RESUME_FOR_CHECKING);
	}

	@Test
	public void save() throws Exception {
		storage.save(new Resume("uuid5"));
		Assert.assertEquals(5, storage.size());
	}

	@Test
	public void delete() throws Exception {
		storage.delete("uuid3");
		Assert.assertEquals(3, storage.size());
	}

	@Test
	public void get() throws Exception {
		Assert.assertEquals(UUID_1, storage.get(UUID_1).getUuid());
	}

	@Test
	public void getAll() throws Exception {
		Resume[] resumes = new Resume[]{new Resume(UUID_1), new Resume(UUID_2),
				new Resume(UUID_3), RESUME_FOR_CHECKING};
		Assert.assertArrayEquals(resumes, storage.getAll());
	}

	@Test
	public void size() throws Exception {
		Resume[] resumes = new Resume[]{new Resume(UUID_1), new Resume(UUID_2),
				new Resume(UUID_3), RESUME_FOR_CHECKING};
		Assert.assertEquals(resumes.length, storage.size());
	}

	@Test(expected = NotExistStorageException.class)
	public void getNotExist() throws Exception {
		storage.get("dummy");
	}

	@Test(expected = ExistStorageException.class)
	public void saveAlreadyExist() throws Exception {
		storage.save(new Resume(UUID_1));
	}

}