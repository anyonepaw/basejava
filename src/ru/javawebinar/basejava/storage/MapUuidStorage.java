package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.*;

public class MapUuidStorage extends AbstractStorage<String> {

	private Map<String, Resume> map = new HashMap<>();

	@Override
	protected void doSave(Resume resume, String key) {
		map.putIfAbsent(resume.getUuid(), resume);
	}

	@Override
	protected Resume doGet(String key) {
		return map.get(key);
	}

	@Override
	protected void doUpdate(Resume resume, String key) {
		map.put(resume.getUuid(), resume);
	}

	@Override
	protected void doDelete(String key) {
		map.remove(key);
	}

	@Override
	protected String getSearchKey(String uuid) {
		return uuid;
	}

	@Override
	public void clear() {
		map.clear();
	}

	@Override
	public List<Resume> doCopyAll() {
		return new ArrayList<>(map.values());
	}

	@Override
	public int size() {
		return map.size();
	}

	@Override
	protected boolean contains(String key) {
		return map.containsKey(key);
	}
}
