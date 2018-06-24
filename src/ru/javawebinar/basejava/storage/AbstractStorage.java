package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Comparator;
import java.util.List;

public abstract class AbstractStorage implements Storage {

	public static final Comparator<Resume> RESUME_COMPARATOR_BY_FULL_NAME =
			Comparator.comparing(Resume::getFullName);

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

	@Override
	public List<Resume> getAllSorted() {
		List<Resume> sortedList = getValues();
		sortedList.sort(RESUME_COMPARATOR_BY_FULL_NAME);
		return sortedList;
	}

	protected abstract List<Resume> getValues();

	protected abstract boolean contains(Object key);

	protected abstract void doSave(Resume resume, Object key);

	protected abstract Resume doGet(Object key);

	protected abstract void doUpdate(Resume resume, Object key);

	protected abstract void doDelete(Object key);

	protected abstract Object getKey(String uuid);

}
