package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

import static java.util.Arrays.binarySearch;

public class SortedArrayStorage extends AbstractArrayStorage {

	@Override
	public void doSave(Resume resume) {
		if (size < 2) {
			storage[size + 1] = resume;
			return;
		}
		binaryInsertion(resume);
	}

	@Override
	public void doDelete(String uuid, int numberOfResume) {
		System.arraycopy(storage, numberOfResume + 1, storage, numberOfResume, size - 1);
	}

	@Override
	protected int findAndCheckIfPresent(String uuid) {
		Resume keyOfSearch = new Resume();
		keyOfSearch.setUuid(uuid);
		return binarySearch(storage, 0, size, keyOfSearch);
	}

	private void binaryInsertion(Resume resume) {
		for (int i = 1; i < size; i++) {
			// Find location to insert using binary search
			int j = Math.abs(Arrays.binarySearch(storage, 0, i, storage[i]) + 1);
			//Shifting array to one location right
			System.arraycopy(storage, j, storage, j + 1, i - (j - 1));
			//Placing element at its correct location
			storage[j] = resume;
		}
	}
}
