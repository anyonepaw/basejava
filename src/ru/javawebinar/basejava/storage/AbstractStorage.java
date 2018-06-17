package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {

	public final void update(Resume resume) {
		String uuid = resume.getUuid();
		doUpdate(resume, isResumeNotExist(uuid));
	}

	public final void save(Resume resume) {
		String uuid = resume.getUuid();
		doSave(resume, isResumeAlreadyExist(uuid));
	}

	public final void delete(String uuid) {
		doDelete(isResumeNotExist(uuid));
	}


	public final Resume get(String uuid) {
		return doGet(isResumeNotExist(uuid));
	}


	private Object isResumeNotExist(String uuid) {
		Object key = getKey(uuid);
		if (contains(key)) {
			return key;
		} else {
			throw new NotExistStorageException(uuid);
		}
	}

	private Object isResumeAlreadyExist(String uuid) {
		Object key = getKey(uuid);
		if (!contains(key)) {
			return key;
		} else {
			throw new ExistStorageException(uuid);
		}
	}

	protected abstract boolean contains(Object key);

	protected abstract void doSave(Resume resume, Object key);

	protected abstract Resume doGet(Object key);

	protected abstract void doUpdate(Resume resume, Object key);

	protected abstract void doDelete(Object key);

	protected abstract Object getKey(String uuid);
}
