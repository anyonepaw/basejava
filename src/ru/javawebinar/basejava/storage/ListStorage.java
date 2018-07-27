package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {

	private List<Resume> arrayList = new ArrayList<>();

	@Override
	protected void doSave(Resume resume, Integer key) {
		arrayList.add(resume);
	}

	@Override
	protected Resume doGet(Integer key) {
		return arrayList.get(key);
	}

	@Override
	protected void doUpdate(Resume resume, Integer key) {
		arrayList.set(key, resume);
	}

	@Override
	protected void doDelete(Integer key) {
		arrayList.remove((int) key);
	}

	@Override
	protected Integer getSearchKey(String uuid) {
		for (int i = 0; i < arrayList.size(); i++) {
			if (arrayList.get(i).getUuid().equals(uuid)) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public void clear() {
		arrayList.clear();
	}

	@Override
	public List<Resume> doCopyAll() {
		return arrayList;
	}

	@Override
	public int size() {
		return arrayList.size();
	}

	@Override
	protected boolean contains(Integer key) {
		return key >= 0;
	}
}
