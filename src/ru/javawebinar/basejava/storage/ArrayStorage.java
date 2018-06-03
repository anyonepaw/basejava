package ru.javawebinar.basejava.storage;

/**
 * Array based storage for Resumes
 */

import ru.javawebinar.basejava.model.Resume;

import java.util.*;


public class ArrayStorage extends AbstractArrayStorage {

	@Override
	public void differentPartOfSave(Resume resume) {
		if (size == AbstractArrayStorage.STORAGE_LIMIT) {
			System.out.println("Storage is full.");
			return;
		}
		if (size < AbstractArrayStorage.STORAGE_LIMIT) {
			storage[size] = resume;
			size++;
		}
	}

	@Override
	public void differentPartOfDelete(String uuid, int numberOfResume) {
		size--;
		storage[numberOfResume] = storage[size];
		storage[size] = null;
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
