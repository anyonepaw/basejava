package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage {

	protected static final int STORAGE_LIMIT = 10000;
	protected Resume[] storage = new Resume[STORAGE_LIMIT];
	protected int size = 0;

	public final void clear() {
		Arrays.fill(storage, 0, size, null);
		size = 0;
	}

	public final void doUpdate(Resume resume, Object index) {
		storage[(int) index] = resume;
	}

	public final void doSave(Resume resume, Object index) {
		if (size == STORAGE_LIMIT) {
			throw new StorageException("Storage is full.", resume.getUuid());
		}
		insert(resume, (int) index);
		size++;
	}

	public final void doDelete(Object index) {
		fillDeletedElement((int) index);
		storage[--size] = null;
	}

	public final Resume doGet(Object index) {
		return storage[(int) index];
	}

	@Override
	public List<Resume> getValues() {
		return Arrays.asList(Arrays.copyOf(storage, size));
	}

	@Override
	protected boolean contains(Object key) {
		return (int) key >= 0;
	}

	public final int size() {
		return size;
	}

	protected abstract Integer getSearchKey(String uuid);

	protected abstract void insert(Resume resume, int index);

	protected abstract void fillDeletedElement(int index);

}