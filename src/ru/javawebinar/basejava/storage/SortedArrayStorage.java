package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

import static java.util.Arrays.binarySearch;

public class SortedArrayStorage extends AbstractArrayStorage {

	@Override
	public void doSave(Resume resume) {
		storage[size] = resume;
		size++;
		binaryInsertionSort();

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

	public void binaryInsertionSort() {
		for (int i = 1; i < size; i++) {
			Resume x = storage[i];
			// Find location to insert using binary search
			int j = Math.abs(Arrays.binarySearch(storage, 0, i, x) + 1);
			//Shifting array to one location right
			System.arraycopy(storage, j, storage, j + 1, i - j);
			//Placing element at its correct location
			storage[j] = x;
		}
	}

}


