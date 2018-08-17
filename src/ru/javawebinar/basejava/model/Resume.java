package ru.javawebinar.basejava.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.*;

/**
 * Resume class
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Resume implements Comparable<Resume>, Serializable {

	private static final long serialVersionUID = 1L;

	// Unique identifier
	private String uuid;

	private String fullName;

	private EnumMap<SectionType, Text> sections = new EnumMap<>(SectionType.class);

	private EnumMap<ContactType, String> contacts = new EnumMap<>(ContactType.class);

	public Resume() {
	}

	public Resume(String fullName) {
		this(UUID.randomUUID().toString(), fullName);
	}

	public Resume(String uuid, String fullName) {
		Objects.requireNonNull(uuid, "uuid must not be null");
		Objects.requireNonNull(fullName, "fullName must not be null");
		this.uuid = uuid;
		this.fullName = fullName;
	}

	public Resume(String uuid, String fullName, EnumMap<ContactType, String> contacts, EnumMap<SectionType, Text> sections) {
		this.uuid = uuid;
		this.fullName = fullName;
		this.sections = sections;
		this.contacts = contacts;
	}

	public String getUuid() {
		return uuid;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Resume resume = (Resume) o;

		if (!uuid.equals(resume.uuid)) return false;
		return fullName.equals(resume.fullName);
	}

	@Override
	public int hashCode() {
		int result = uuid.hashCode();
		result = 31 * result + fullName.hashCode();
		return result;
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


	public String getContact(ContactType type) {
		return contacts.get(type);
	}

	public Text getSection(SectionType type) {
		return sections.get(type);
	}

	public void addContact(ContactType type, String value) {
		contacts.put(type, value);
	}

	public void addSection(SectionType type, Text section) {
		sections.put(type, section);
	}

	public EnumMap<SectionType, Text> getSections() {
		return sections;
	}

	public EnumMap<ContactType, String> getContacts() {
		return contacts;
	}
}
