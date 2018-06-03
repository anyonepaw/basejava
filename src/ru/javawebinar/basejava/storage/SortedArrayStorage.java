package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;
import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {


	@Override
	public void differentPartOfSave(Resume resume) {
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
	public void differentPartOfDelete(String uuid, int numberOfResume) {
		System.arraycopy(storage, numberOfResume + 1, storage, numberOfResume, size - 1);
		storage[size--] = null;
	}

	@Override
	protected int findAndCheckIfPresent(String uuid) {
		Resume keyOfSearch = new Resume();
		keyOfSearch.setUuid(uuid);
		//уже делает rangeCheck
		return Arrays.binarySearch(storage, 0, size, keyOfSearch);
	}
}
