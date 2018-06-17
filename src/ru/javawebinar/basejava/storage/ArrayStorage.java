package ru.javawebinar.basejava.storage;

/**
 * Array based storage for Resumes
 */

import ru.javawebinar.basejava.model.Resume;
import java.util.*;

public class ArrayStorage extends AbstractArrayStorage {

	@Override
	public void insert(Resume resume, int index) {
		storage[size] = resume;
	}

	@Override
	public void fillDeletedElement(int index) {
		storage[index] = storage[size - 1];
	}

	protected Integer getKey(String uuid) {
		Objects.requireNonNull(uuid, "You have typed an empty uuid!");
		for (int i = 0; i < size; i++) {
			if (uuid.equals(storage[i].getUuid())) {
				return i;
			}
		}
		return -1;
	}



}
