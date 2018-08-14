package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class AbstractPathStorage extends AbstractStorage<Path> {
	private Path directory;


	protected AbstractPathStorage(String dir) {
		directory = Paths.get(dir);
		Objects.requireNonNull(directory, "directory must not be null");
		if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
			throw new IllegalArgumentException(dir + " is not directory or is not writable");
		}
	}

	@Override
	protected List<Resume> doCopyAll() {
		List<Path> paths;
		try {
			paths = Files.list(directory).collect(Collectors.toList());
		} catch (IOException e) {
			throw new StorageException("Couldn't show a list of resumes in path: " + directory.toAbsolutePath(), directory.getFileName().toString(), e);
		}
		List<Resume> resumes = new ArrayList<>();
		for (Path path : paths) {
			resumes.add(doGet(path));
		}
		return resumes;
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
			resume = doRead(Files.newInputStream(path));
		} catch (IOException e) {
			throw new StorageException("File read error", path.getFileName().toString(), e);
		}
		return resume;
	}


	protected abstract void doWrite(Resume r, OutputStream os) throws IOException;

	protected abstract Resume doRead(InputStream is) throws IOException;


	@Override
	protected void doUpdate(Resume resume, Path path) {
		try {
			doWrite(resume, Files.newOutputStream(path));
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
		return new File(directory.toFile(), uuid).toPath();
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
		int size;
		try {
			size = (int) Files.list(directory).count();
		} catch (IOException e) {
			throw new StorageException("Getting path size error", null);
		}
		return size;
	}
}
