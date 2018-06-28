package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.*;

public class MapStorage extends AbstractStorage {

	private Map<String, Resume> map = new HashMap<>();

	@Override
	protected void doSave(Resume resume, Object key) {
		map.put(resume.getUuid(), resume);
	}

	@Override
	protected Resume doGet(Object key) {
		Resume resume = (Resume) key;
		return map.get(resume.getUuid());
	}

	@Override
	protected void doUpdate(Resume resume, Object key) {
		map.put(resume.getUuid(), resume);
	}

	@Override
	protected void doDelete(Object key) {
		Resume resume = (Resume) key;
		map.remove(resume.getUuid());
	}

	@Override
	public Object getSearchKey(String uuid) {

		for (Resume resume : map.values()) {
			if (resume.getUuid().equals(uuid)) {
				return resume;
			}
		}
		return null;
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
	protected boolean contains(Object key) {
		if(!(key == null)) {
			Resume resume = (Resume) key;
			return map.containsKey(resume.getUuid());
		}else {
			return false;
		}
	}

	@Override
	public int size() {
		return map.size();
	}


}
