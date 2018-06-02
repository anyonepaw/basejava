

public abstract class AbstractArrayStorage implements Storage {


	protected static final int STORAGE_LIMIT = 10000;
	protected Resume[] storage = new Resume[STORAGE_LIMIT];
	protected int size = 0;


	public int size() {

		return size;

	}


	public Resume get(String uuid) {

		int numberOfResume = findAndCheckIfPresent(uuid);

		if (numberOfResume >= 0) {
			return storage[numberOfResume];
		}
		System.out.println("Resume with uuid = " + uuid + " is not present.");
		return null;

	}


	protected abstract int findAndCheckIfPresent(String uuid);


}
