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

        int i = findAndCheckIfPresent(resume.uuid);
        if (i != -1) {
            storage[i] = new Resume();
        } else {
            System.out.println("Resume on " + resume.uuid + " is not present.");
        }

    }


    protected void save(Resume resume) {

        if (findAndCheckIfPresent(resume.uuid) >= 0) {
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

        int i = findAndCheckIfPresent(uuid);
        if (i != -1) {
            return storage[i];
        } else{
            System.out.println("Resume on " + uuid + " is not present.");
            return null;
        }

    }


    protected void delete(String uuid) {

        int i = findAndCheckIfPresent(uuid);
        if (i != -1) {
            size--;
            storage[i] = storage[size];
            storage[size] = null;
        } else {
            System.out.println("Resume on " + uuid + " is not present.");
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


    private int findAndCheckIfPresent(String uuid) {

        if (uuid != null) {
            for (int i = 0; i < size; i++) {
                if (uuid.equals(storage[i].uuid)) {
                    return i;
                }
            }
        }
        return -1;

    }


}
