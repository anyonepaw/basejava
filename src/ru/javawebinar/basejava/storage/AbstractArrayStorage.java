package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {

	protected static final int STORAGE_LIMIT = 10000;
	protected Resume[] storage = new Resume[STORAGE_LIMIT];
	protected int size = 0;

	public final void clear() {
		Arrays.fill(storage, 0, size, null);
		size = 0;
	}

	public final void update(Resume resume) {
		int numberOfResume = findAndCheckIfPresent(resume.getUuid());
		if (numberOfResume >= 0) {
			storage[numberOfResume] = resume;
		} else {
			System.out.println("Resume with uuid = " + resume.getUuid() + " is not present.");
		}
	}

	public final void save(Resume resume) {
		if (size == STORAGE_LIMIT) {
			System.out.println("Storage is full.");
			return;
		}
		int numberOfResume = findAndCheckIfPresent(resume.getUuid());
		if (numberOfResume >= 0) {
			System.out.println("Resume is present in storage already.");
			return;
		}
		doSave(resume, numberOfResume);
		size++;
	}

	public final void delete(String uuid) {
		int numberOfResume = findAndCheckIfPresent(uuid);
		if (numberOfResume >= 0) {
			doDelete(numberOfResume);
			storage[--size] = null;
		} else {
			System.out.println("Resume with uuid = " + uuid + " is not present.");
		}
	}

	public final Resume get(String uuid) {
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
	public final Resume[] getAll() {
		return Arrays.copyOf(storage, size);
	}

	public final int size() {
		return size;
	}

	protected abstract int findAndCheckIfPresent(String uuid);

	protected abstract void doSave(Resume resume, int numberOfResume);

	protected abstract void doDelete(int numberOfResume);

}
