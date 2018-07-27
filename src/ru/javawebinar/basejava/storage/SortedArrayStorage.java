package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Comparator;

import static java.util.Arrays.binarySearch;

public class SortedArrayStorage extends AbstractArrayStorage {

	private static final Comparator<Resume> RESUME_COMPARATOR =
			Comparator.comparing(Resume::getUuid);

	@Override
	public void insert(Resume resume, int index) {
		int indexOfResume = Math.abs(index + 1);
		System.arraycopy(storage, indexOfResume, storage, indexOfResume + 1, size - indexOfResume);
		storage[indexOfResume] = resume;
	}

	@Override
	public void fillDeletedElement(int index) {
		System.arraycopy(storage, index + 1, storage, index, size - index - 1);
	}

	@Override
	protected Integer getSearchKey(String uuid) {
		Resume keyOfSearch = new Resume(uuid, "");
		return binarySearch(storage, 0, size, keyOfSearch, RESUME_COMPARATOR);
	}


}


