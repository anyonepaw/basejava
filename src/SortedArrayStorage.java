import java.util.Arrays;


public class SortedArrayStorage extends AbstractArrayStorage {


	public void save(Resume resume) {

		int numberOfResume = findAndCheckIfPresent(resume.uuid);

		if (numberOfResume >= 0) {
			System.out.println("Resume is present in storage already.");
			return;
		}
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


	public void delete(String uuid) {

		int numberOfResume = findAndCheckIfPresent(uuid);

		if (numberOfResume >= 0) {
			System.arraycopy(storage, numberOfResume+1, storage, numberOfResume, size-1);
			storage[size--] = null;
		} else {
			System.out.println("Resume with uuid = " + uuid + " is not present.");
		}

	}


	@Override
	protected int findAndCheckIfPresent(String uuid) {

		Resume keyOfSearch = new Resume();
		keyOfSearch.uuid = uuid;
		//уже делает rangeCheck
		return Arrays.binarySearch(storage, 0, size, keyOfSearch);

	}

}
