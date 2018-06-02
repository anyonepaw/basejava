/**
 * Array based storage for Resumes
 */

import java.util.*;


public class ArrayStorage implements Storage {


	private static final int STORAGE_LIMIT = 10000;
	private Resume[] storage = new Resume[STORAGE_LIMIT];
	private int size = 0;


	public void clear() {

		Arrays.fill(storage, 0, size, null);
		size = 0;

	}


	public void update(Resume resume) {

		int numberOfResume = findAndCheckIfPresent(resume.uuid);

		if (numberOfResume >= 0) {
			storage[numberOfResume] = resume;
		} else {
			System.out.println("Resume with uuid = " + resume.uuid + " is not present.");
		}

	}


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


	public Resume get(String uuid) {

		int numberOfResume = findAndCheckIfPresent(uuid);

		if (numberOfResume >= 0) {
			return storage[numberOfResume];
		}
		System.out.println("Resume with uuid = " + uuid + " is not present.");
		return null;

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


	/**
	 * @return array, contains only Resumes in storage (without null)
	 */
	public Resume[] getAll() {

		return Arrays.copyOf(storage, size);

	}


	public int size() {

		return size;

	}


	private int findAndCheckIfPresent(String uuid) {

		Objects.requireNonNull(uuid, "You have typed an empty uuid!");
		for (int i = 0; i < size; i++) {
			if (uuid.equals(storage[i].uuid)) {
				return i;
			}
		}
		return -1;

	}


}
