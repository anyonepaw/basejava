package ru.javawebinar.basejava.model;


import java.util.*;

/**
 * Resume class
 */
public class Resume implements Comparable<Resume> {

	// Unique identifier
	private final String uuid;

	private final String fullName;

	private Map<SectionType, Text> sections = new LinkedHashMap<>();

	private Map<String, String> contacts = new LinkedHashMap<>();


	public Resume(String fullName) {
		this(UUID.randomUUID().toString(), fullName);
	}

	public Resume(String uuid, String fullName) {
		Objects.requireNonNull(uuid, "uuid must not be null");
		Objects.requireNonNull(fullName, "fullName must not be null");
		this.uuid = uuid;
		this.fullName = fullName;
	}

	public String getUuid() {
		return uuid;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Resume resume = (Resume) o;

		return uuid.equals(resume.uuid);
	}

	@Override
	public int hashCode() {
		return uuid.hashCode();
	}

	@Override
	public String toString() {
		return uuid;
	}

	public String getFullName() {
		return uuid + '(' + fullName + ')';
	}

	@Override
	public int compareTo(Resume r) {
		int cmp = fullName.compareTo(r.fullName);
		return cmp != 0 ? cmp : uuid.compareTo(r.uuid);
	}

	public Map<SectionType, Text> getSections() {
		return sections;
	}

	public Map<String, String> getContacts() {
		return contacts;
	}

}
