package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {

	public final void update(Resume resume) {
		doUpdate(resume, ifResumeNotExist(resume.getUuid()));
	}

	public final void save(Resume resume) {
		doSave(resume, ifResumeAlreadyExist(resume.getUuid()));
	}

	public final void delete(String uuid) {
		doDelete(ifResumeNotExist(uuid));
	}


	public final Resume get(String uuid) {
		return doGet(ifResumeNotExist(uuid));
	}

	protected abstract boolean contains(Object key);

	protected abstract void doSave(Resume resume, Object key);

	protected abstract Resume doGet(Object key);

	protected abstract void doUpdate(Resume resume, Object key);

	protected abstract void doDelete(Object key);

	protected abstract Object getKey(String uuid);

	private Object ifResumeNotExist(String uuid) {
		Object key = getKey(uuid);
		if (contains(key)) {
			return key;
		} else {
			throw new NotExistStorageException(uuid);
		}
	}

	private Object ifResumeAlreadyExist(String uuid) {
		Object key = getKey(uuid);
		if (!contains(key)) {
			return key;
		} else {
			throw new ExistStorageException(uuid);
		}
	}
}
