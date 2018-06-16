package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage implements Storage {

	protected static final int STORAGE_LIMIT = 10000;
	protected Resume[] storage = new Resume[STORAGE_LIMIT];
	protected int size = 0;

	public final void clear() {
		Arrays.fill(storage, 0, size, null);
		size = 0;
	}

	public void doUpdate(Resume resume, Object numberOfResume) {
		storage[(Integer) numberOfResume] = resume;
	}

	public void doSave(Resume resume, Object numberOfResume) {
		if (size == STORAGE_LIMIT) {
			throw new StorageException("Storage is full.", resume.getUuid());
		}
		insert(resume, (Integer) numberOfResume);
		size++;
	}

	public void doDelete(Object numberOfResume) {
		fillDeletedElement((Integer) numberOfResume);
		storage[--size] = null;
	}

	public Resume doGet(Object numberOfResume) {
		return storage[(Integer)numberOfResume];
	}

	public final Resume[] getAll() {
		return Arrays.copyOf(storage, size);
	}

	public final int size() {
		return size;
	}

	protected abstract Integer getKey(String uuid);

	protected abstract void insert(Resume resume, int numberOfResume);

	protected abstract void fillDeletedElement(int numberOfResume);

	@Override
	protected boolean contains(Object key) {
		return (Integer) key >= 0;
	}
}
