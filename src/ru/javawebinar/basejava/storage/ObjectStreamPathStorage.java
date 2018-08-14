package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.io.*;
import java.nio.file.Path;

public class ObjectStreamPathStorage extends AbstractPathStorage implements StrategyWriteRead {

	protected ObjectStreamPathStorage(Path directory) {
		super(directory.toAbsolutePath().toString());
	}

	@Override
	public void doWrite(Resume r, OutputStream os) throws IOException {
		try (ObjectOutputStream oos = new ObjectOutputStream(os)) {
			oos.writeObject(r);
		}
	}

	@Override
	public Resume doRead(InputStream is) throws IOException {
		try (ObjectInputStream ois = new ObjectInputStream(is)) {
			return (Resume) ois.readObject();
		} catch (ClassNotFoundException e) {
			throw new StorageException("Error read resume", null, e);
		}
	}


}
