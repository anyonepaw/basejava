package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.*;

public class MapStorage extends AbstractStorage {

	private Map<Resume, Resume> map = new HashMap<>();

	@Override
	protected void doSave(Resume resume, Object key) {
			map.put(resume, resume);
	}

	@Override
	protected Resume doGet(Object key) {
		return map.get(key);
	}

	@Override
	protected void doUpdate(Resume resume, Object key) {
		map.put((Resume) key, resume);
	}

	@Override
	protected void doDelete(Object key) {
		map.remove(key);
	}

	@Override
	protected Object getKey(String uuid) {

		for (Resume item : map.keySet()){
			if(item.getUuid().equals(uuid)){
				return item;
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
		return map.containsKey(key);
	}

	@Override
	public int size() {
		return map.size();
	}


}
