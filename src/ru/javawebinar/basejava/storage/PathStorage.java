package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.streamStrategy.StreamStrategy;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PathStorage extends AbstractStorage<Path> {
	private Path directory;
	private StreamStrategy streamStrategy;

	protected PathStorage(String dir, StreamStrategy streamStrategy) {
		directory = Paths.get(dir);
		Objects.requireNonNull(directory, "directory must not be null");
		if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
			throw new IllegalArgumentException(dir + " is not directory or is not writable");
		}
		this.streamStrategy = streamStrategy;
	}

	@Override
	protected List<Resume> doCopyAll() {
		try {
			return Files.list(directory).map(this::doGet).collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected boolean contains(Path path) {
		return Files.exists(path);
	}

	@Override
	protected void doSave(Resume resume, Path path) {
		doUpdate(resume, path);
	}

	@Override
	protected Resume doGet(Path path) {
		Resume resume;
		try {
			resume = streamStrategy.doRead(new BufferedInputStream(Files.newInputStream(path)));
		} catch (IOException e) {
			throw new StorageException("File read error", path.getFileName().toString(), e);
		}
		return resume;
	}

	@Override
	protected void doUpdate(Resume resume, Path path) {
		try {
			streamStrategy.doWrite(resume, (new BufferedOutputStream(Files.newOutputStream(path))));
		} catch (IOException e) {
			throw new StorageException("File write error", path.getFileName().toString(), e);
		}
	}

	@Override
	protected void doDelete(Path path) {
		try {
			Files.delete(path);
		} catch (IOException e) {
			throw new StorageException("Couldn't create a file: " + path.toAbsolutePath(), path.getFileName().toString(), e);
		}
	}

	@Override
	protected Path getSearchKey(String uuid) {
		return directory.resolve("/uuid");
	}

	@Override
	public void clear() {
		try {
			Files.list(directory).forEach(this::doDelete);
		} catch (IOException e) {
			throw new StorageException("Path delete error", null);
		}
	}

	@Override
	public int size() {
		try {
			return (int) Files.list(directory).count();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
