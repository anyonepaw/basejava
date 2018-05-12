/**
 * Array based storage for Resumes
 */


import java.util.Arrays;

/**
 * Реализуйте класс ArrayStorage, организовав хранение резюме на основе массива
 * с методами save, get, delete, size, clear, getAll
 * <p>
 * При этом храните все резюме в начале storage (без дырок в виде null), чтобы не
 * перебирать каждый раз все 10000 элементов
 * <p>
 * Схема хранения резюме в storage (от 0 до size - 1 элементов null нет):
 * r1, r2, r3,..., rn, null, null,..., null
 * <----- size ----->
 * <------- storage.length (10000) ------->
 * <p>
 * Посмотрите на класс java.util.Arrays. В нем есть полезные методы, которые помогут
 * вам написать более простой и понятный код
 * Протестируйте вашу реализацию с помощью классов MainArray.main() и MainTestArrayStorage.main()
 */


public class ArrayStorage {

    private int size = 0;

    private Resume[] storage = new Resume[10000];


    void clear() {

        Arrays.fill(storage, 0, size, null);
        size = 0;

    }


    void save(Resume r) {

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


}
