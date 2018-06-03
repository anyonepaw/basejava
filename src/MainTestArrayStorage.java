/**
 * Test for com.urise.webapp.storage.ArrayStorage
 */
public class MainTestArrayStorage {
    static final Storage ARRAY_STORAGE = new SortedArrayStorage();

    public static void main(String[] args) {
        final Resume r1 = new Resume();
        r1.uuid = "uuid1";
        final Resume r2 = new Resume();
        r2.uuid = "uuid2";
        final Resume r3 = new Resume();
        r3.uuid = "uuid3";

        ARRAY_STORAGE.save(r1);
        ARRAY_STORAGE.save(r2);
        ARRAY_STORAGE.save(r3);

        System.out.println("Get r1: " + ARRAY_STORAGE.get(r1.uuid));
        System.out.println("Get r2: " + ARRAY_STORAGE.get(r2.uuid));
        System.out.println("Get r3: " + ARRAY_STORAGE.get(r3.uuid));
        System.out.println("Size: " + ARRAY_STORAGE.size());

        System.out.println("Get dummy: " + ARRAY_STORAGE.get("dummy"));

        printAll();

        ARRAY_STORAGE.delete(r1.uuid);
        printAll();
        ARRAY_STORAGE.delete(r3.uuid);
        printAll();
        ARRAY_STORAGE.clear();
        printAll();

        ARRAY_STORAGE.update(r2);

        System.out.println("Size: " + ARRAY_STORAGE.size());
    }

    static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : ARRAY_STORAGE.getAll()) {
            System.out.println(r);
        }
    }
}
