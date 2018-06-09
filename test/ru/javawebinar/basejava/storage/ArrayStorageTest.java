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

}
