package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.*;

public class MapStorage extends AbstractStorage<Resume> {

	private Map<String, Resume> map = new HashMap<>();

	@Override
	protected void doSave(Resume resume, Resume key) {
		map.put(resume.getUuid(), resume);
	}

	@Override
	protected Resume doGet(Resume key) {
		return key;
	}

	@Override
	protected void doUpdate(Resume resume, Resume key) {
		map.put(resume.getUuid(), resume);
	}

	@Override
	protected void doDelete(Resume key) {
		map.remove(key.getUuid());
	}

	@Override
	public Resume getSearchKey(String uuid) {
		return map.get(uuid);
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
	protected boolean contains(Resume key) {
		return key != null;
	}

	@Override
	public int size() {
		return map.size();
	}


}
