package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapStorage extends AbstractStorage {

	private Map<Integer, Resume> map = new HashMap<>();

	@Override
	protected void doSave(Resume resume, Object key) {
			map.put((Integer) key, resume);
	}

	@Override
	protected Resume doGet(Object key) {
		return map.get(key);
	}

	@Override
	protected void doUpdate(Resume resume, Object key) {
		map.put((Integer) key, resume);
	}

	@Override
	protected void doDelete(Object key) {
		map.remove(key);
	}

	@Override
	protected Object getKey(String uuid) {
		return uuid.hashCode();
	}

	@Override
	public void clear() {
		map.clear();
	}

	@Override
	public List<Resume> getAllSorted() {
		List<Resume> sortedList = new ArrayList<>(map.values());
		sortedList.sort(RESUME_COMPARATOR_BY_FULL_NAME);
		return sortedList;
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
