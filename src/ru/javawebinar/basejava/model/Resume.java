package ru.javawebinar.basejava.model;

import javafx.print.PageLayout;

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

	public static final Resume EMPTY = new Resume();

	static {
		EMPTY.setSection(SectionType.PERSONAL, PlainText.EMPTY);
		EMPTY.setSection(SectionType.OBJECTIVE, PlainText.EMPTY);
		EMPTY.setSection(SectionType.ACHIEVEMENT, StringListText.EMPTY);
		EMPTY.setSection(SectionType.QUALIFICATIONS, StringListText.EMPTY);
		EMPTY.setSection(SectionType.EDUCATION, new OrganizationText(Organization.EMPTY));
		EMPTY.setSection(SectionType.EXPERIENCE, new OrganizationText(Organization.EMPTY));
	}

	// Unique identifier
	private String uuid;

	private String fullName;

	private Map<SectionType, Text> sections = new EnumMap<>(SectionType.class);
	private Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);

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

	public String getUuid() {
		return uuid;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Resume resume = (Resume) o;
		return Objects.equals(uuid, resume.uuid) &&
				Objects.equals(fullName, resume.fullName) &&
				Objects.equals(contacts, resume.contacts) &&
				Objects.equals(sections, resume.sections);
	}


	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	@Override
	public int hashCode() {
		return Objects.hash(uuid, fullName, contacts, sections);
	}

	@Override
	public String toString() {
		return uuid;
	}

	public String getFullName() {
		return fullName;
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



	public void setContact(ContactType type, String value) {
		contacts.put(type, value);
	}

	public void setSection(SectionType type, Text text) {
		sections.put(type, text);
	}

	public Map<SectionType, Text> getSections() {
		return sections;
	}

	public Map<ContactType, String> getContacts() {
		return contacts;
	}
}
