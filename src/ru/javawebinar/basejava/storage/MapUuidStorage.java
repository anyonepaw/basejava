package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.*;

public class MapUuidStorage extends AbstractStorage {

	private Map<String, Resume> map = new HashMap<>();

	@Override
	protected void doSave(Resume resume, Object key) {
		map.putIfAbsent(resume.getUuid(), resume);
	}

	@Override
	protected Resume doGet(Object key) {
		return map.get(key);
	}

	@Override
	protected void doUpdate(Resume resume, Object key) {
		map.put(resume.getUuid(), resume);
	}

	@Override
	protected void doDelete(Object key) {
		map.remove(key);
	}

	@Override
	protected Object getSearchKey(String uuid) {
		return uuid;
	}

	@Override
	public void clear() {
		map.clear();
	}

	@Override
	public List<Resume> getValues() {
		return new ArrayList<>(map.values());
	}

	@Override
	public int size() {
		return map.size();
	}

	@Override
	protected boolean contains(Object key) {
		return map.containsKey(key);
	}
}
