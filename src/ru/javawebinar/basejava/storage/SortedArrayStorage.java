package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import static java.util.Arrays.binarySearch;

public class SortedArrayStorage extends AbstractArrayStorage {

	@Override
	public void insertElement(Resume resume, int numberOfResume) {
		int indexOfResume = Math.abs(numberOfResume + 1);
		System.arraycopy(storage, indexOfResume, storage, indexOfResume + 1, size - numberOfResume);
		storage[indexOfResume] = resume;
	}

	@Override
	public void fillDeletedElement(int numberOfResume) {
		System.arraycopy(storage, numberOfResume + 1, storage, numberOfResume, size - numberOfResume - 1);
	}

	@Override
	protected int findAndCheckIfPresent(String uuid) {
		Resume keyOfSearch = new Resume(uuid);
		return binarySearch(storage, 0, size, keyOfSearch);
	}

}


