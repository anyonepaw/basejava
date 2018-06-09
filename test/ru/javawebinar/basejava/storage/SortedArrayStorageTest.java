package ru.javawebinar.basejava.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.javawebinar.basejava.model.Resume;

public class SortedArrayStorageTest extends AbstractArrayStorageTest {

	private static SortedArrayStorage sortedArrayStorage = new SortedArrayStorage();

	@Before
	public void setUp() throws Exception {
		sortedArrayStorage.clear();
		sortedArrayStorage.save(new Resume(UUID_1));
		sortedArrayStorage.save(new Resume(UUID_2));
		sortedArrayStorage.save(new Resume(UUID_3));
	}

	public SortedArrayStorageTest() {
		super(sortedArrayStorage);
	}

	@Test
	public void insertElement() throws Exception {
		sortedArrayStorage.insertElement(RESUME_FOR_CHECKING, getIndex(RESUME_FOR_CHECKING.getUuid()));
		sortedArrayStorage.size++;
		Resume[] resumes = new Resume[]{new Resume(UUID_1),
				new Resume(UUID_2), new Resume(UUID_3), RESUME_FOR_CHECKING};
		Assert.assertArrayEquals(resumes, sortedArrayStorage.getAll());
	}

	@Test
	public void fillDeletedElement() throws Exception {
		sortedArrayStorage.fillDeletedElement(getIndex("uuid3"));
		sortedArrayStorage.storage[--sortedArrayStorage.size] = null;
		Resume[] resumes = new Resume[]{new Resume(UUID_1), new Resume(UUID_2)};
		Assert.assertArrayEquals(resumes, sortedArrayStorage.getAll());
	}

	@Test
	public void findAndCheckIfPresent() throws Exception {
		Assert.assertEquals(0, sortedArrayStorage.findAndCheckIfPresent("uuid1"));
	}


	private int getIndex(String uuid) {
		return sortedArrayStorage.findAndCheckIfPresent(uuid);
	}
}
