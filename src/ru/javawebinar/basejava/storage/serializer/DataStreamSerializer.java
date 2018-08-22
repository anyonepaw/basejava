package ru.javawebinar.basejava.storage.serializer;

import ru.javawebinar.basejava.model.*;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class DataStreamSerializer implements StreamSerializer {

	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	@Override
	public void doWrite(Resume resume, OutputStream os) throws IOException {
		try (DataOutputStream dos = new DataOutputStream(os)) {
			dos.writeUTF(resume.getUuid());
			dos.writeUTF(resume.getFullName());
			dos.writeInt(resume.getContacts().size());

			for (Map.Entry<ContactType, String> entry : resume.getContacts().entrySet()) {
				writeString(dos, entry.getKey().name());
				writeString(dos, entry.getValue());
			}
			dos.writeInt(resume.getSections().size());
			for (Map.Entry<SectionType, Text> entry : resume.getSections().entrySet()) {
				dos.writeUTF(entry.getKey().name());
				textWriter(entry.getKey(), entry.getValue(), dos);
			}
		}
	}

	private void writeString(DataOutputStream dos, String s) {
		try {
			dos.writeUTF(s);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Resume doRead(InputStream is) throws IOException {
		try (DataInputStream dis = new DataInputStream(is)) {
			Resume resume = new Resume(dis.readUTF(), dis.readUTF());
			int contactSize = dis.readInt();
			for (int i = 0; i < contactSize; i++) {
				resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
			}
			int sectionSize = dis.readInt();
			for (int i = 0; i < sectionSize; i++) {
				SectionType sectionType = SectionType.valueOf(dis.readUTF());
				switch (sectionType) {
					case PERSONAL:
					case OBJECTIVE:
						resume.addSection(sectionType, new PlainText(dis.readUTF()));
						break;
					case ACHIEVEMENT:
					case QUALIFICATIONS:
						int stringListTextSize = dis.readInt();
						List<String> stringListText = new ArrayList<>();
						for (int j = 0; j < stringListTextSize; j++) {
							stringListText.add(dis.readUTF());
						}
						resume.addSection(sectionType, new StringListText(stringListText));
						break;
					case EDUCATION:
					case EXPERIENCE:
						resume.addSection(sectionType, organizationTextReader(dis));
						break;
				}
			}
			return resume;
		}
	}

	private void textWriter(SectionType sectionType, Text text, DataOutputStream dos) throws IOException {
		switch (sectionType) {
			case PERSONAL:
			case OBJECTIVE:
				dos.writeUTF(text.toString());
				break;
			case ACHIEVEMENT:
			case QUALIFICATIONS:
				List<String> stringListText = ((StringListText) text).getStringList();
				dos.writeInt(stringListText.size());
				for (String line : stringListText) {
					dos.writeUTF(line);
				}
				break;
			case EDUCATION:
			case EXPERIENCE:
				List<Organization> orgList = ((OrganizationText) text).getOrgList();
				dos.writeInt(orgList.size());
				for (Organization organization : orgList) {
					dos.writeUTF(organization.getHomePage().getName());
					dos.writeUTF(organization.getHomePage().getUrl());
					List<Organization.Job> jobList = organization.getJobList();
					dos.writeInt(jobList.size());
					for (Organization.Job aJobList : jobList) {
						dos.writeUTF(aJobList.getFromDate().format(FORMATTER));
						dos.writeUTF(aJobList.getToDate().format(FORMATTER));
						dos.writeUTF(aJobList.getTitle());
						dos.writeUTF(aJobList.getDescription());
					}
				}
				break;
		}
	}

	private Text organizationTextReader(DataInputStream dis) throws IOException {
		int orgListSize = dis.readInt();
		List<Organization> orgList = new ArrayList<>();
		for (int i = 0; i < orgListSize; i++) {
			String title = dis.readUTF();
			String link = (dis.readUTF());
			int sizeOfJobList = dis.readInt();
			List<Organization.Job> jobList = new ArrayList<>();
			for (int j = 0; j < sizeOfJobList; j++) {
				String date1 = dis.readUTF();
				String date2 = dis.readUTF();
				String jobTitle = dis.readUTF();
				String jobDesc = dis.readUTF();
				jobList.add(new Organization.Job(
						LocalDate.parse(date1, FORMATTER),
						LocalDate.parse(date2, FORMATTER),
						jobTitle, jobDesc));
			}
			orgList.add(new Organization(new Link(title, link), jobList));
		}
		return new OrganizationText(orgList);
	}

}
