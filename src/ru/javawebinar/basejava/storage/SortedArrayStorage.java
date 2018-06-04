package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

	@Override
	public void doSave(Resume resume) {
		if (size == 0) {
			storage[size++] = resume;
			return;
		}
		if (storage[size - 1].compareTo(resume) > 0) {
			storage[size] = storage[size - 1];
			storage[size - 1] = resume;
			size++;
		} else {
			storage[size++] = resume;
		}
	}

	@Override
	public void doDelete(String uuid, int numberOfResume) {
		for (int i = numberOfResume; i < size; i++) {
			storage[i] = storage[i + 1];
		}
	}

	@Override
	protected int findAndCheckIfPresent(String uuid) {
		Resume keyOfSearch = new Resume();
		keyOfSearch.setUuid(uuid);
		//уже делает rangeCheck
		return Arrays.binarySearch(storage, 0, size, keyOfSearch);
	}

}
