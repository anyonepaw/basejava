package ru.javawebinar.basejava.storage;

/**
 * Array based storage for Resumes
 */

import ru.javawebinar.basejava.model.Resume;
import java.util.*;


public class ArrayStorage extends AbstractArrayStorage {

	public void save(Resume resume) {
		int numberOfResume = findAndCheckIfPresent(resume.getUuid());
		if (numberOfResume >= 0) {
			System.out.println("Resume is present in storage already.");
			return;
		}
		if (size == AbstractArrayStorage.STORAGE_LIMIT) {
			System.out.println("Storage is full.");
			return;
		}
		if (size < AbstractArrayStorage.STORAGE_LIMIT) {
			storage[size] = resume;
			size++;
		}
	}

	public void delete(String uuid) {
		int numberOfResume = findAndCheckIfPresent(uuid);
		if (numberOfResume >= 0) {
			size--;
			storage[numberOfResume] = storage[size];
			storage[size] = null;
		} else {
			System.out.println("Resume with uuid = " + uuid + " is not present.");
		}
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
