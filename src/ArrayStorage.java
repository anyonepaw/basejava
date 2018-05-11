/**
 * Array based storage for Resumes
 */


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
    Resume[] storage = new Resume[10000];


    /**
     * Removes all elements
     */
    void clear() {
        storage = new Resume[10000];
        size = 0;
    }

    /**
     * Adds a new Resume
     *
     * @param r
     */
    void save(Resume r) {

        //блок необходим, чтобы в массив не был сохранен
        // r.uuid = null (человек с пустым резюме) при команде save
        try {
            if (!r.uuid.equals("null")) {
                //r1, r2, r3,..., rn, null <- add a new element here
                storage[size] = r;
                size++;
            }
        } catch (NullPointerException e) {
            e.getMessage();
        }
    }

    /**
     * Gets resume by it's uuid
     *
     * @param uuid
     * @return Resume
     */

    Resume get(String uuid) {

        if (uuid != null) {
            for (int i = 0; i < size; i++) {
                if (uuid.equals(storage[i].uuid))
                    return storage[i];
            }
        }
        return null;

    }

    void delete(String uuid) {

        //(get(uuid) не возвращает индекс)

        //получается перегруженным циклами, но метод
        //get(uuid) != null нужен здесь, чтобы temp не удалял 0-й элемент по дефолту
        //Он нужен, чтобы выяснить, что такое резюме точно существует и можно получить его индекс

        try {
            if (size != 0 && get(uuid) != null) {

                int temp = 0;

                for (int i = 0; i < size; i++) {
                    if (uuid.equals(storage[i].uuid)) {
                        temp = i; //find idx of element
                    }

                }

                //shift

                //r1 r2 r3 r4 r5
                //[] [] [] [x] []
                //r1 r2 r3 r4(5)
                //[] [] [] []

                for (int j = temp; j < size - 1; j++) {
                    storage[j] = storage[j + 1];
                }

                size--;
            }
        } catch (IllegalStateException e) {
            e.getMessage();
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume newArray[] = new Resume[size];
        System.arraycopy(storage, 0, newArray, 0, size);
        return newArray;
    }

    int size() {
        return size;
    }


}
