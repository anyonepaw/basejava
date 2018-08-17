package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.storage.serializer.ObjectStreamStorage;

public class ObjectPathStorageTest extends AbstractStorageTest {

	public ObjectPathStorageTest() {
		super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new ObjectStreamStorage()));
	}

}
