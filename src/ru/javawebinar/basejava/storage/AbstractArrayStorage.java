package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
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
			throw new NotExistStorageException(resume.getUuid());
		}
	}

	public final void save(Resume resume) {
		if (size == STORAGE_LIMIT) {
			throw new StorageException("Storage is full.", resume.getUuid());
		}
		int numberOfResume = findAndCheckIfPresent(resume.getUuid());
		if (numberOfResume >= 0) {
			throw new ExistStorageException(resume.getUuid());
		}
		insertElement(resume, numberOfResume);
		size++;
	}

	public final void delete(String uuid) {
		int numberOfResume = findAndCheckIfPresent(uuid);
		if (numberOfResume >= 0) {
			fillDeletedElement(numberOfResume);
			storage[--size] = null;
		} else {
			throw new NotExistStorageException(uuid);
		}
	}

	public final Resume get(String uuid) {
		int numberOfResume = findAndCheckIfPresent(uuid);
		if (numberOfResume >= 0) {
			return storage[numberOfResume];
		}
		throw new NotExistStorageException(uuid);
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

	protected abstract void insertElement(Resume resume, int numberOfResume);

	protected abstract void fillDeletedElement(int numberOfResume);

}
