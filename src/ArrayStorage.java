/**
 * Array based storage for Resumes
 */

import java.util.*;


public class ArrayStorage extends AbstractArrayStorage {


	public void save(Resume resume) {

		int numberOfResume = findAndCheckIfPresent(resume.uuid);

		if (numberOfResume >= 0) {
			System.out.println("Resume is present in storage already.");
			return;
		}
		if (size == STORAGE_LIMIT) {
			System.out.println("Storage is full.");
			return;
		}
		if (size < STORAGE_LIMIT) {
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
			if (uuid.equals(storage[i].uuid)) {
				return i;
			}
		}
		return -1;

	}


}
