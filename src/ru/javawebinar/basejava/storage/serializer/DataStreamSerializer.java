package ru.javawebinar.basejava.storage.serializer;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.*;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class DataStreamSerializer implements StreamSerializer {

	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	@Override
	public void doWrite(Resume resume, OutputStream os) throws IOException {
		System.out.println(resume.toString());
		try (DataOutputStream dos = new DataOutputStream(os)) {
			dos.writeUTF(resume.getUuid());
			dos.writeUTF(resume.getFullName());
			dos.writeInt(resume.getContacts().size());
			resume.getContacts().forEach((key, value) -> {
				try {
					dos.writeUTF(key.name());
					dos.writeUTF(value);
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
			for (Map.Entry<SectionType, Text> entry : resume.getSections().entrySet()) {
				dos.writeUTF(entry.getKey().name());
				Text text = entry.getValue();
				if (text.getClass().equals(OrganizationText.class)) {
					organizationTextWriter(text, dos);
				} else {
					dos.writeUTF(text.toString());
				}
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
			System.out.println(resume.toString());
			return resume;
		}
	}


	private void organizationTextWriter(Text text, DataOutputStream dos) {
		try {
			List<Organization> orgList = ((OrganizationText) text).getOrgList();
			dos.writeInt(orgList.size());
			for (int i = 0; i < orgList.size(); i++) {
				Organization org = orgList.get(i);

				dos.writeUTF(org.getHomePage().getName());
				dos.writeUTF(isNull(org.getHomePage().getUrl()));
				dos.writeInt(org.getJobList().size());
				for (int j = 0; j < org.getJobList().size(); j++) {
					Organization.Job job = org.getJobList().get(j);
					dos.writeUTF(job.getFromDate().format(FORMATTER));
					dos.writeUTF(job.getToDate().format(FORMATTER));
					dos.writeUTF(job.getTitle());
					dos.writeUTF(isNull(job.getDescription()));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Text organizationTextReader(DataInputStream dis) {

		try {
			int sizeOfOrgList = dis.readInt();
			List<Organization> orgList = new ArrayList<>();
			for (int i = 0; i < sizeOfOrgList; i++) {
				String title = dis.readUTF();
				String link = isEmpty(dis.readUTF());
				int sizeOfJobList = dis.readInt();
				List<Organization.Job> jobList = new ArrayList<>();
				for (int j = 0; j < sizeOfJobList; j++) {
					jobList.add(new Organization.Job(
							LocalDate.parse(dis.readUTF(), FORMATTER),
							LocalDate.parse(dis.readUTF(), FORMATTER),
							dis.readUTF(), isEmpty(dis.readUTF())));
				}
				orgList.add(new Organization(new Link(title, link), jobList));
			}
			return new OrganizationText(orgList);
		} catch (IOException e) {
			throw new StorageException("");
		}
	}

	private String isNull(String f) {
		return f == null ? "" : f;
	}

	private String isEmpty(String f) {
		return f.equals("")? null : f;
	}

	private void enumRead(Resume resume, int size, DataInputStream dis, Class clazz) {
		try {
			if (clazz.equals(ContactType.class)) {
				for (int i = 0; i < size; i++) {
					resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
				}
			}
			if (clazz.equals(SectionType.class)) {
				resume.addSection(SectionType.valueOf(dis.readUTF()), new PlainText(dis.readUTF()));
				resume.addSection(SectionType.valueOf(dis.readUTF()), new PlainText(dis.readUTF()));
				resume.addSection(SectionType.valueOf(dis.readUTF()), new StringListText((dis.readUTF()).split("\n")));
				resume.addSection(SectionType.valueOf(dis.readUTF()), new StringListText((dis.readUTF()).split("\n")));
				resume.addSection(SectionType.valueOf(dis.readUTF()), organizationTextReader(dis));
				resume.addSection(SectionType.valueOf(dis.readUTF()), organizationTextReader(dis));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
