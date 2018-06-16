package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {

	private List<Resume> arrayList = new ArrayList<>();

	@Override
	protected boolean contains(Object key) {
		return arrayList.contains(key);
	}

	@Override
	protected void doSave(Resume resume, Object key) {
		arrayList.add((Integer) key, resume);
	}

	@Override
	protected Resume doGet(Object key) {
		return arrayList.get((Integer) key);
	}

	@Override
	protected void doUpdate(Resume resume, Object key) {
		arrayList.add((Integer) key, resume);
	}

	@Override
	protected void doDelete(Object key) {
		arrayList.remove(key);
	}

	@Override
	protected Object getKey(String uuid) {
		return arrayList.indexOf(uuid);
	}

	@Override
	public void clear() {
		arrayList.clear();
	}

	@Override
	public Resume[] getAll() {
		return arrayList.toArray(new Resume[arrayList.size()]);
	}

	@Override
	public int size() {
		return arrayList.size();
	}


}
