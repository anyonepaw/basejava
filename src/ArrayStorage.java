/**
 * Array based storage for Resumes
 */

import java.util.Arrays;


public class ArrayStorage {


    private Resume[] storage = new Resume[10000];
    private int size = 0;

    void clear() {

        Arrays.fill(storage, 0, size, null);
        size = 0;

    }

    void update(Resume r) {

        if (!resumeIsPresent(r.uuid)) {
            System.out.println("Resume is not present. Cannot update.");
            return;
        }

        for (int i = 0; i < size; i++) {
            if (r.uuid.equals(storage[i].uuid)) {
                r = new Resume();
            }
        }

    }


    void save(Resume r) {

        if (resumeIsPresent(r.uuid)) {
            System.out.println("Resume is present in storage already.");
            return;
        }

        if (size == storage.length) {
            System.out.println("Storage is full.");
            return;
        }

        if (size < storage.length) {
            storage[size] = r;
            size++;
        }

    }


    Resume get(String uuid) {

        if (uuid != null) {
            for (int i = 0; i < size; i++) {
                if (uuid.equals(storage[i].uuid)) {
                    return storage[i];

                }

            }
        }
        return null;

    }


    void delete(String uuid) {

        if (!resumeIsPresent(uuid)) {
            System.out.println("Resume is not present. Cannot delete.");
            return;
        }

        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].uuid)) {
                storage[i] = storage[size - 1];
                storage[size - 1] = null;
                break;
            }
        }
        size--;

    }


    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {

        return Arrays.copyOf(storage, size);

    }

    int size() {

        return size;

    }

    private boolean resumeIsPresent(String uuid) {

        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].uuid)) {
                return true;
            }
        }
        return false;

    }


}
