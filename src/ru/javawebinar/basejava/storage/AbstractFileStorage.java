package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Link;
import ru.javawebinar.basejava.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {
	private File directory;

	protected AbstractFileStorage(File directory) {
		Objects.requireNonNull(directory, "directory must not be null");
		if (directory.isDirectory()) {
			throw new IllegalArgumentException(directory.getAbsolutePath() + "is not directory");
		}
		if (!directory.canRead() || !directory.canWrite()) {
			throw new IllegalArgumentException(directory.getAbsolutePath() + "is not readable/writable");
		}
		this.directory = directory;
	}

	@Override
	protected List<Resume> doCopyAll() {
		List<Resume> resumes = new ArrayList<>();
		try {
			for (File file : directory.listFiles()) {
				if (file.isFile()) {
				  resumes.add(doGet(file));
				}
			}
		}catch (NullPointerException e){
			throw new StorageException("Directory is empty: ", directory.getAbsolutePath(), e);
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
			doWrite(resume, file);
		} catch (IOException e) {
			throw new StorageException("IO Error", file.getName(), e);
		}
	}

	protected abstract void doWrite(Resume r, File file) throws IOException;


	@Override
	protected Resume doGet(File file) {
		Resume resume;
		try {
			resume = doRead(file);
		} catch (IOException e) {
			throw new StorageException("IO Error", file.getName(), e);
		}
		return resume;
	}

	protected abstract Resume doRead(File file) throws IOException;

	@Override
	protected void doUpdate(Resume resume, File file) {
			doSave(resume, file);
	}

	@Override
	protected void doDelete(File file) {
		if(!file.delete()){
			throw new StorageException("Cannot delete a file: ", file.getName());
		}
	}

	@Override
	protected File getSearchKey(String uuid) {
		return new File(directory, uuid);
	}

	@Override
	public void clear() {
		try {
			for (File file : directory.listFiles()) {
				if (file.isFile()) {
					if(!file.delete()){
						throw new StorageException("Cannot delete a file: ", file.getName());
					}
				}
			}
		} catch (NullPointerException e){
			throw new StorageException("Directory is empty: ", directory.getAbsolutePath(), e);
		}
	}

	@Override
	public int size() {
		int numberOfFilesInDirectory = 0;
		try {
			for (File file : directory.listFiles()) {
				if (file.isFile()) {
					numberOfFilesInDirectory++;
				}
			}
		}catch (NullPointerException e){
			throw new StorageException("Directory is empty: ", directory.getAbsolutePath(), e);
		}
		return numberOfFilesInDirectory;
	}
}
