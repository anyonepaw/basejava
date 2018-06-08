package ru.javawebinar.basejava.storage;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.model.Resume;

public class ArrayStorageTest extends AbstractArrayStorageTest {

	private static final String UUID_1 = "uuid1";
	private static final String UUID_2 = "uuid2";
	private static final String UUID_3 = "uuid3";
	private static final Resume RESUME_FOR_CHECKING = new Resume("uuid4");

	@Override
	ArrayStorage getStorage() {
		return new ArrayStorage();
	}

	private ArrayStorage arrayStorage = getStorage();

	@Before
	public void setUp() throws Exception {
		arrayStorage.save(new Resume(UUID_1));
		arrayStorage.save(new Resume(UUID_2));
		arrayStorage.save(new Resume(UUID_3));
	}

	@Test
	public void insertElement() {
		arrayStorage.insertElement(RESUME_FOR_CHECKING, arrayStorage.size);
		arrayStorage.size++;
		Resume[] resumes = new Resume[]{new Resume(UUID_1),
				new Resume(UUID_2), new Resume(UUID_3), RESUME_FOR_CHECKING};
		Assert.assertArrayEquals(resumes, arrayStorage.getAll());
	}

	@Test
	public void fillDeletedElement() {
		int index = arrayStorage.findAndCheckIfPresent("uuid3");
		arrayStorage.fillDeletedElement(index);
		arrayStorage.storage[--arrayStorage.size] = null;
		Resume[] resumes = new Resume[]{new Resume(UUID_1), new Resume(UUID_2)};
		Assert.assertArrayEquals(resumes, arrayStorage.getAll());
	}

	@Test
	public void findAndCheckIfPresent(){
		Assert.assertEquals(0, arrayStorage.findAndCheckIfPresent("uuid1"));
	}


}
