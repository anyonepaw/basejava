package ru.javawebinar.basejava.storage;

import org.junit.*;
import ru.javawebinar.basejava.model.Resume;

public class ArrayStorageTest extends AbstractArrayStorageTest {

	private static ArrayStorage arrayStorage = new ArrayStorage();

	@Before
	public void setUp() throws Exception {
		arrayStorage.clear();
		arrayStorage.save(new Resume(UUID_1));
		arrayStorage.save(new Resume(UUID_2));
		arrayStorage.save(new Resume(UUID_3));
	}

	public ArrayStorageTest() {
		super(arrayStorage);
	}

	@Test
	public void insertElement() throws Exception {
		arrayStorage.insertElement(RESUME_FOR_CHECKING, arrayStorage.size);
		arrayStorage.size++;
		Resume[] resumes = new Resume[]{new Resume(UUID_1),
				new Resume(UUID_2), new Resume(UUID_3), RESUME_FOR_CHECKING};
		Assert.assertArrayEquals(resumes, arrayStorage.getAll());
	}

	@Test
	public void fillDeletedElement() throws Exception{
		int index = arrayStorage.findAndCheckIfPresent("uuid3");
		arrayStorage.fillDeletedElement(index);
		arrayStorage.storage[--arrayStorage.size] = null;
		Resume[] resumes = new Resume[]{new Resume(UUID_1), new Resume(UUID_2)};
		Assert.assertArrayEquals(resumes, arrayStorage.getAll());
	}

	@Test
	public void findAndCheckIfPresent() throws Exception {
		Assert.assertEquals(0, arrayStorage.findAndCheckIfPresent("uuid1"));
	}


}
