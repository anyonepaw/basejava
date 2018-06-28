package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {

	private List<Resume> arrayList = new ArrayList<>();

	@Override
	protected void doSave(Resume resume, Object key) {
		arrayList.add(resume);
	}

	@Override
	protected Resume doGet(Object key) {
		return arrayList.get((int) key);
	}

	@Override
	protected void doUpdate(Resume resume, Object key) {
		arrayList.set((int) key, resume);
	}

	@Override
	protected void doDelete(Object key) {
		arrayList.remove(((int) key));
	}

	@Override
	protected Integer getSearchKey(String uuid) {
		for (int i = 0; i < arrayList.size(); i++) {
			if(arrayList.get(i).getUuid().equals(uuid)){
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
	public List<Resume> getValues() {
		return arrayList;
	}

	@Override
	public int size() {
		return arrayList.size();
	}

	@Override
	protected boolean contains(Object key) {
		return (int) key >= 0;
	}
}
