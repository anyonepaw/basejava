package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {

	protected static final int STORAGE_LIMIT = 10000;
	protected Resume[] storage = new Resume[STORAGE_LIMIT];
	protected int size = 0;

	public void clear() {
		Arrays.fill(storage, 0, size, null);
		size = 0;
	}

	public void update(Resume resume) {
		int numberOfResume = findAndCheckIfPresent(resume.getUuid());
		if (numberOfResume >= 0) {
			storage[numberOfResume] = resume;
		} else {
			System.out.println("Resume with uuid = " + resume.getUuid() + " is not present.");
		}
	}

	public void save(Resume resume){
		int numberOfResume = findAndCheckIfPresent(resume.getUuid());
		if (numberOfResume >= 0) {
			System.out.println("Resume is present in storage already.");
			return;
		}
		differentPartOfSave(resume);
	}

	public abstract void differentPartOfSave(Resume resume);


	public void delete(String uuid){
		int numberOfResume = findAndCheckIfPresent(uuid);
		if (numberOfResume >= 0) {
			differentPartOfDelete(uuid, numberOfResume);
		} else {
			System.out.println("Resume with uuid = " + uuid + " is not present.");
		}
	}

	public abstract void differentPartOfDelete(String uuid, int numberOfResume);

	public Resume get(String uuid) {
		int numberOfResume = findAndCheckIfPresent(uuid);
		if (numberOfResume >= 0) {
			return storage[numberOfResume];
		}
		System.out.println("Resume with uuid = " + uuid + " is not present.");
		return null;
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

	protected abstract int findAndCheckIfPresent(String uuid);

}
