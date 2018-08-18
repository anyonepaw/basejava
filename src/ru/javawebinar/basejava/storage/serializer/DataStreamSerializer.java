package ru.javawebinar.basejava.storage.serializer;

import ru.javawebinar.basejava.model.ContactType;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.model.SectionType;

import java.io.*;
import java.util.Map;

public class DataStreamSerializer implements StreamSerializer {

	@Override
	public void doWrite(Resume r, OutputStream os) throws IOException {
		try (DataOutputStream dos = new DataOutputStream(os)) {
			dos.writeUTF(r.getUuid());
			dos.writeUTF(r.getFullName());
			Map<ContactType, String> contacts = r.getContacts();
			dos.writeInt(contacts.size());
			for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
				dos.writeUTF(entry.getKey().name());
				dos.writeUTF(entry.getValue());
			}

		}
	}

	@Override
	public Resume doRead(InputStream is) throws IOException {
		try (DataInputStream dis = new DataInputStream(is)) {
			Resume resume = new Resume(dis.readUTF(), dis.readUTF());
			int size = dis.readInt();
			enumRead(resume, size, dis, ContactType.class);
			enumRead(resume, size, dis, SectionType.class);
			return resume;
		}
	}

	private void enumRead(Resume resume, int size, DataInputStream dis, Class clazz) {
		try {
			for (int i = 0; i < size; i++) {
				if (clazz.equals(ContactType.class)) {
					resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
