package ru.javawebinar.basejava.storage.serializer;

import ru.javawebinar.basejava.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.*;


public class DataStreamSerializer implements StreamSerializer {

	@Override
	public void doWrite(Resume resume, OutputStream os) throws IOException {
		try (DataOutputStream dos = new DataOutputStream(os)) {
			dos.writeUTF(resume.getUuid());
			dos.writeUTF(resume.getFullName());
			Map<ContactType, String> contacts = resume.getContacts();
			writeCollection(dos, contacts.entrySet(), entry -> {
				dos.writeUTF(entry.getKey().name());
				dos.writeUTF(entry.getValue());
			});

			writeCollection(dos, resume.getSections().entrySet(), entry -> {
				SectionType type = entry.getKey();
				Text text = entry.getValue();
				dos.writeUTF(type.name());
				switch (type) {
					case PERSONAL:
					case OBJECTIVE:
						dos.writeUTF(((PlainText) text).getContent());
						break;
					case ACHIEVEMENT:
					case QUALIFICATIONS:
						writeCollection(dos, ((StringListText) text).getItems(), dos::writeUTF);
						break;
					case EXPERIENCE:
					case EDUCATION:
						writeCollection(dos, ((OrganizationText) text).getOrgList(), org -> {
							dos.writeUTF(org.getHomePage().getName());
							dos.writeUTF(org.getHomePage().getUrl());
							writeCollection(dos, org.getJobList(), position -> {
								writeLocalDate(dos, position.getFromDate());
								writeLocalDate(dos, position.getToDate());
								dos.writeUTF(position.getTitle());
								dos.writeUTF(position.getDescription());
							});
						});
						break;
				}
			});
		}
	}

	private void writeLocalDate(DataOutputStream dos, LocalDate ld) throws IOException {
		dos.writeInt(ld.getYear());
		dos.writeInt(ld.getMonth().getValue());
	}

	@Override
	public Resume doRead(InputStream is) throws IOException {
		try (DataInputStream dis = new DataInputStream(is)) {
			Resume resume = new Resume(dis.readUTF(), dis.readUTF());
			readItems(dis, () -> resume.setContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));
			readItems(dis, () -> {
				SectionType sectionType = SectionType.valueOf(dis.readUTF());
				resume.setSection(sectionType, readSection(dis, sectionType));
			});
			return resume;
		}
	}

	private Text readSection(DataInputStream dis, SectionType sectionType) throws IOException {
		switch (sectionType) {
			case PERSONAL:
			case OBJECTIVE:
				return new PlainText(dis.readUTF());
			case ACHIEVEMENT:
			case QUALIFICATIONS:
				return new StringListText(readList(dis, dis::readUTF));
			case EXPERIENCE:
			case EDUCATION:
				return new OrganizationText(
						readList(dis, () -> new Organization(
								new Link(dis.readUTF(), dis.readUTF()),
								readList(dis, () -> new Organization.Job(
										readLocalDate(dis), readLocalDate(dis), dis.readUTF(), dis.readUTF()
								))
						)));
			default:
				throw new IllegalStateException();
		}
	}

	private LocalDate readLocalDate(DataInputStream dis) throws IOException {
		return LocalDate.of(dis.readInt(), dis.readInt(), 1);
	}

	private <T> List<T> readList(DataInputStream dis, ElementReader<T> reader) throws IOException {
		int size = dis.readInt();
		List<T> list = new ArrayList<>(size);
		for (int i = 0; i < size; i++) {
			list.add(reader.read());
		}
		return list;
	}


	private interface ElementProcessor {
		void process() throws IOException;
	}

	private interface ElementReader<T> {
		T read() throws IOException;
	}

	private interface ElementWriter<T> {
		void write(T t) throws IOException;
	}

	private void readItems(DataInputStream dis, ElementProcessor elementProcessor) throws IOException {
		int size = dis.readInt();
		for (int i = 0; i < size; i++) {
			elementProcessor.process();
		}
	}

	private <T> void writeCollection(DataOutputStream dos, Collection<T> collection, ElementWriter<T> writer) throws IOException {
		dos.writeInt(collection.size());
		for (T item : collection) {
			writer.write(item);
		}
	}
}
