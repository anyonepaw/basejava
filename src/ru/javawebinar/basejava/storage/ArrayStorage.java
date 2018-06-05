package ru.javawebinar.basejava.storage;

/**
 * Array based storage for Resumes
 */

import ru.javawebinar.basejava.model.Resume;

import java.util.*;


public class ArrayStorage extends AbstractArrayStorage {

	@Override
	public void doSave(Resume resume) {
			storage[size] = resume;
	}

	@Override
	public void doDelete(String uuid, int numberOfResume) {
		storage[numberOfResume] = storage[size - 1];
	}

	protected int findAndCheckIfPresent(String uuid) {
		Objects.requireNonNull(uuid, "You have typed an empty uuid!");
		for (int i = 0; i < size; i++) {
			if (uuid.equals(storage[i].getUuid())) {
				return i;
			}
		}
		return -1;
	}

}
