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

        int numberOfResume = findAndCheckIfPresent(resume.uuid);
        if (numberOfResume >= 0) {
            storage[numberOfResume] = resume;
        } else {
            System.out.println("Resume " + resume.uuid + " is not present.");
        }
    }


    protected void save(Resume resume) {

        int numberOfResume = findAndCheckIfPresent(resume.uuid);

        if (numberOfResume >= 0) {
            System.out.println("Resume is present in storage already.");
            return;
        }
        if (numberOfResume == -2) {
            System.out.println("You try to save empty resume.");
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

        int numberOfResume = findAndCheckIfPresent(uuid);
        if (numberOfResume >= 0) {
            return storage[numberOfResume];
        } else {
            System.out.println("Resume " + uuid + " is not present.");
        }
        return null;

    }


    protected void delete(String uuid) {

        int numberOfResume = findAndCheckIfPresent(uuid);
        if (numberOfResume >= 0) {
            size--;
            storage[numberOfResume] = storage[size];
            storage[size] = null;
        } else {
            System.out.println("Resume " + uuid + " is not present.");
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
            return -1;
        }
        return -2;

    }


}
