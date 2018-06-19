package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Map;
import java.util.TreeMap;

public class MapStorage extends AbstractStorage {

	private Map<String, Resume> map = new TreeMap<>();

	@Override
	protected void doSave(Resume resume, Object key) {

	}

	@Override
	protected Resume doGet(Object key) {
		return null;
	}

	@Override
	protected void doUpdate(Resume resume, Object key) {

	}

	@Override
	protected void doDelete(Object key) {

	}

	@Override
	protected Object getKey(String uuid) {
		return uuid;
	}

	@Override
	public void clear() {

	}

	@Override
	public Resume[] getAll() {
		return new Resume[0];
	}

	@Override
	public int size() {
		return 0;
	}
}
