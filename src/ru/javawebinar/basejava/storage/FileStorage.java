package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.serializer.StreamSerializer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileStorage extends AbstractStorage<File> {
	private File directory;
	private StreamSerializer streamSerializer;

	protected FileStorage(File directory, StreamSerializer streamSerializer) {
		Objects.requireNonNull(directory, "directory must not be null");
		this.streamSerializer = streamSerializer;
		if (!directory.isDirectory()) {
			throw new IllegalArgumentException(directory.getAbsolutePath() + " is not a directory");
		}
		if (!directory.canRead() || !directory.canWrite()) {
			throw new IllegalArgumentException(directory.getAbsolutePath() + "is not readable/writable");
		}
		this.directory = directory;

	}

	@Override
	protected List<Resume> doCopyAll() {
		List<Resume> resumes = new ArrayList<>();
		for (File file : directoryArray()) {
			resumes.add(doGet(file));
		}
		return resumes;
	}

	@Override
	protected boolean contains(File file) {
		return file.exists();
	}

	@Override
	protected void doSave(Resume resume, File file) {
		try {
			file.createNewFile();
		} catch (IOException e) {
			throw new StorageException("Couldn't create a file: " + file.getAbsolutePath(), file.getName(), e);
		}
		doUpdate(resume, file);
	}


	@Override
	protected Resume doGet(File file) {
		Resume resume;
		try {
			resume = streamSerializer.doRead(new BufferedInputStream(new FileInputStream(file)));
		} catch (IOException e) {
			throw new StorageException("File read error", file.getName(), e);
		}
		return resume;
	}


	@Override
	protected void doUpdate(Resume resume, File file) {
		try {
			streamSerializer.doWrite(resume, new BufferedOutputStream(new FileOutputStream(file)));
		} catch (IOException e) {
			throw new StorageException("File write error", file.getName(), e);
		}
	}

	@Override
	protected void doDelete(File file) {
		if (!file.delete()) {
			throw new StorageException("Couldn't delete a file: ", file.getName());
		}
	}

	@Override
	protected File getSearchKey(String uuid) {
		return new File(directory, uuid);
	}

	@Override
	public void clear() {
		for (File file : directoryArray()) {
			doDelete(file);
		}
	}

	@Override
	public int size() {
		return directoryArray().length;
	}

	private File[] directoryArray() {
		File[] files = directory.listFiles();
		if (files == null) {
			throw new StorageException("Directory is empty");
		}
		return files;
	}
}
