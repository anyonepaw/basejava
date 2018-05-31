/**
 * Array based storage for Resumes
 */

import java.util.Arrays;


public class ArrayStorage {

    private Resume[] storage = new Resume[10000];
    private int size = 0;


    protected void clear() {

        Arrays.fill(storage, 0, size, null);
        size = 0;

    }

    protected void update(Resume resume) {

        int i = resumeIsPresent(resume.uuid);
        if (i != -1) storage[i] = new Resume();

    }


    protected void save(Resume resume) {

        if (resumeIsPresent(resume.uuid) >= 0) {
            System.out.println("Resume is present in storage already.");
            return;
        }
        if (size == storage.length) {
            System.out.println("Storage is full.");
            return;
        }
        if (size < storage.length) {
            storage[size] = resume;
            size++;
        }

    }


    protected Resume get(String uuid) {

        if (uuid != null) {
            int i = resumeIsPresent(uuid);
            if (i != -1) return storage[i];
        }
        return null;

    }


    protected void delete(String uuid) {

        int i = resumeIsPresent(uuid);
        if (i != -1) {
            storage[i] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        }

    }


    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {

        return Arrays.copyOf(storage, size);

    }

    protected int size() {

        return size;

    }


    private int resumeIsPresent(String uuid) {

        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].uuid)) {
                return i;
            }
        }
        System.out.println("Resume is not present.");
        return -1;

    }


}
