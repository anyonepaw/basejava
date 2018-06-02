import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {


	@Override
	public void clear() {

	}


	@Override
	public void update(Resume resume) {

	}


	@Override
	public void save(Resume resume) {

	}


	@Override
	public void delete(String uuid) {

	}


	@Override
	public Resume[] getAll() {
		return new Resume[0];
	}


	@Override
	protected int findAndCheckIfPresent(String uuid) {
		Resume keyOfSearch = new Resume();
		keyOfSearch.uuid = uuid;
		return Arrays.binarySearch(storage, 0, size, keyOfSearch);
	}

}
