package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<SK> implements Storage {

//	private static final Comparator<Resume> RESUME_COMPARATOR_BY_FULL_NAME = Comparator.comparing
//			(Resume::getFullName).thenComparing(Resume::getUuid);
	private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

	protected abstract List<Resume> doCopyAll();

	protected abstract boolean contains(SK key);

	protected abstract void doSave(Resume resume, SK key);

	protected abstract Resume doGet(SK key);

	protected abstract void doUpdate(Resume resume, SK key);

	protected abstract void doDelete(SK key);

	protected abstract SK getSearchKey(String uuid);

	public final void update(Resume resume) {
		LOG.info("Update " + resume);
		String uuid = resume.getUuid();
		doUpdate(resume, checkIfResumeNotExist(uuid));
	}

	public final void save(Resume resume) {
		LOG.info("Save " + resume);
		String uuid = resume.getUuid();
		doSave(resume, checkIfResumeAlreadyExist(uuid));
	}

	public final void delete(String uuid) {
		LOG.info("Delete " + uuid);
		doDelete(checkIfResumeNotExist(uuid));
	}

	public final Resume get(String uuid) {
		LOG.info("Get " + uuid);
		return doGet(checkIfResumeNotExist(uuid));
	}

	private SK checkIfResumeNotExist(String uuid) {
		SK key = getSearchKey(uuid);
		if (contains(key)) {
			return key;
		} else {
			LOG.warning("Resume with uuid = " + uuid + " is not exist.");
			throw new NotExistStorageException(uuid);
		}
	}

	private SK checkIfResumeAlreadyExist(String uuid) {
		SK key = getSearchKey(uuid);
		if (!contains(key)) {
			return key;
		} else {
			LOG.warning("Resume with uuid = " + uuid + " is already exist.");
			throw new ExistStorageException(uuid);
		}
	}

	@Override
	public List<Resume> getAllSorted() {
		LOG.info("getAllSorted");
		List<Resume> sortedList = doCopyAll();
		Collections.sort(sortedList);
		return sortedList;
	}


}
