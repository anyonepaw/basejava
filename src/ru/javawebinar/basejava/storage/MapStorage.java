package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.*;

public class MapStorage extends AbstractStorage {

	private Map<String, Resume> map = new HashMap<>();
	private List<String> listOfKeys;

	@Override
	protected void doSave(Resume resume, Object key) {
		map.put(resume.getUuid(), resume);
	}

	@Override
	protected Resume doGet(Object key) {
		return map.get(listOfKeys.get((int) key));
	}

	@Override
	protected void doUpdate(Resume resume, Object key) {
		map.put(listOfKeys.get((int) key), resume);
	}

	@Override
	protected void doDelete(Object key) {
		map.remove(listOfKeys.get((int) key));
	}

	@Override
	public Object getSearchKey(String uuid) {
		listOfKeys = new ArrayList<>(map.keySet());
		return listOfKeys.contains(uuid) ? listOfKeys.indexOf(uuid) : -1;
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
		return (int) key >= 0;
	}

	@Override
	public int size() {
		return map.size();
	}


}
