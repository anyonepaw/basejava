package ru.javawebinar.basejava.storage;

import org.junit.Before;
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

}
