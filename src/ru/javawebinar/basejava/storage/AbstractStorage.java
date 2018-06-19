package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {

	public final void update(Resume resume) {
		String uuid = resume.getUuid();
		doUpdate(resume, checkIfResumeNotExist(uuid));
	}

	public final void save(Resume resume) {
		String uuid = resume.getUuid();
		doSave(resume, checkIfResumeAlreadyExist(uuid));
	}

	public final void delete(String uuid) {
		doDelete(checkIfResumeNotExist(uuid));
	}


	public final Resume get(String uuid) {
		return doGet(checkIfResumeNotExist(uuid));
	}

	private boolean contains(Object key){
		return (int) key >= 0;
	}

	private Object checkIfResumeNotExist(String uuid) {
		Object key = getKey(uuid);
		if (contains(key)) {
			return key;
		} else {
			throw new NotExistStorageException(uuid);
		}
	}

	private Object checkIfResumeAlreadyExist(String uuid) {
		Object key = getKey(uuid);
		if (!contains(key)) {
			return key;
		} else {
			throw new ExistStorageException(uuid);
		}
	}

	protected Object getKey(String uuid){
		return uuid;
	}

	protected abstract void doSave(Resume resume, Object key);

	protected abstract Resume doGet(Object key);

	protected abstract void doUpdate(Resume resume, Object key);

	protected abstract void doDelete(Object key);

}
