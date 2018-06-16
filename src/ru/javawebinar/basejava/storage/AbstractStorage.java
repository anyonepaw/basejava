package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {

	public final void update(Resume resume) {
		Object key = getKey(resume.getUuid());
		if (contains(key)) {
			doUpdate(resume, key);
		} else {
			throw new NotExistStorageException(resume.getUuid());
		}
	}

	public final void save(Resume resume) {
		Object key = getKey(resume.getUuid());
		if (contains(key)) {
			throw new ExistStorageException(resume.getUuid());
		} else {
			doSave(resume, key);
		}
	}

	public final void delete(String uuid) {
		Object key = getKey(uuid);
		if (contains(key)) {
			doDelete(key);
		} else {
			throw new NotExistStorageException(uuid);
		}
	}

	public final Resume get(String uuid) {
		Object key = getKey(uuid);
		if (contains(key)) {
			return doGet(key);
		}
		throw new NotExistStorageException(uuid);
	}


	protected abstract boolean contains(Object key);

	protected abstract void doSave(Resume resume, Object key);

	protected abstract Resume doGet(Object key);

	protected abstract void doUpdate(Resume resume, Object key);

	protected abstract void doDelete(Object key);

	protected abstract Object getKey(String uuid);

}
