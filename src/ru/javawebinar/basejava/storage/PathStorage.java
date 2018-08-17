package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.serializer.StreamSerializer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> {
	private Path directory;
	private StreamSerializer streamSerializer;

	protected PathStorage(String dir, StreamSerializer streamSerializer) {
		Objects.requireNonNull(dir, "directory must not be null");
		directory = Paths.get(dir);
		this.streamSerializer = streamSerializer;
		if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
			throw new IllegalArgumentException(dir + " is not directory or is not writable");
		}
	}

	@Override
	protected List<Resume> doCopyAll() {
		return getFilesList().map(this::doGet).collect(Collectors.toList());
	}

	@Override
	protected boolean contains(Path path) {
		return Files.isRegularFile(path);
	}

	@Override
	protected void doSave(Resume resume, Path path) {

		try {
			Files.createFile(path);
		} catch (IOException e) {
			throw new StorageException("Couldn't create a path " + path, getFileName(path), e);
		}
		doUpdate(resume, path);
	}

	@Override
	protected Resume doGet(Path path) {
		try {
			return streamSerializer.doRead(new BufferedInputStream(Files.newInputStream(path)));
		} catch (IOException e) {
			throw new StorageException("Path read error: ", getFileName(path), e);
		}
	}

	@Override
	protected void doUpdate(Resume resume, Path path) {
		try {
			streamSerializer.doWrite(resume, new BufferedOutputStream(Files.newOutputStream(path)));
		} catch (IOException e) {
			throw new StorageException("Path write error: ", resume.getUuid(), e);
		}
	}

	@Override
	protected void doDelete(Path path) {
		try {
			Files.delete(path);
		} catch (IOException e) {
			throw new StorageException("Path delete error: ", getFileName(path), e);
		}
	}

	@Override
	protected Path getSearchKey(String uuid) {
		return directory.resolve("/uuid");
	}

	@Override
	public void clear() {
		getFilesList().forEach(this::doDelete);
	}

	@Override
	public int size() {
		return (int) getFilesList().count();
	}

	private String getFileName(Path path) {
		return path.getFileName().toString();
	}


	private Stream<Path> getFilesList() {
		try {
			return Files.list(directory);
		} catch (IOException e) {
			throw new StorageException("Directory read error", null, e);
		}
	}
}
