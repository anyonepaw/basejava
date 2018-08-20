package ru.javawebinar.basejava.storage.serializer;

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
		try (DataOutputStream dos = new DataOutputStream(os)) {
			dos.writeUTF(resume.getUuid());
			dos.writeUTF(resume.getFullName());
			dos.writeInt(resume.getContacts().size());
			dos.writeInt(resume.getSections().size());
			for (Map.Entry<ContactType, String> entry : resume.getContacts().entrySet()) {
				dos.writeUTF(entry.getKey().name());
				dos.writeUTF(entry.getValue());
			}
			for (Map.Entry<SectionType, Text> entry : resume.getSections().entrySet()) {
				SectionType sectionType = entry.getKey();
				dos.writeUTF(sectionType.name());
				textWriter(sectionType, entry.getValue(), dos);
			}
		}
	}

	@Override
	public Resume doRead(InputStream is) throws IOException {
		try (DataInputStream dis = new DataInputStream(is)) {
			Resume resume = new Resume(dis.readUTF(), dis.readUTF());
			int contactSize = dis.readInt();
			int sectionSize = dis.readInt();
			for (int i = 0; i < contactSize; i++) {
				resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
			}
			for (int i = 0; i < sectionSize; i++) {
				SectionType sectionType = SectionType.valueOf(dis.readUTF());
				switch (sectionType) {
					case PERSONAL:
					case OBJECTIVE:
						resume.addSection(sectionType, new PlainText(dis.readUTF()));
						break;
					case ACHIEVEMENT:
					case QUALIFICATIONS:
						resume.addSection(sectionType, new StringListText((dis.readUTF()).split("\n")));
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
			case EDUCATION:
			case EXPERIENCE:
				List<Organization> orgList = ((OrganizationText) text).getOrgList();
				dos.writeInt(orgList.size());
				for (Organization organization : orgList) {
					dos.writeUTF(organization.getHomePage().getName());
					dos.writeUTF(isNull(organization.getHomePage().getUrl()));
					List<Organization.Job> jobList = organization.getJobList();
					dos.writeInt(jobList.size());
					for (Organization.Job aJobList : jobList) {
						dos.writeUTF(aJobList.getFromDate().format(FORMATTER));
						dos.writeUTF(aJobList.getToDate().format(FORMATTER));
						dos.writeUTF(aJobList.getTitle());
						dos.writeUTF(isNull(aJobList.getDescription()));
					}
				}
				break;
			default:
				dos.writeUTF(text.toString());
				break;
		}
	}


	private Text organizationTextReader(DataInputStream dis) throws IOException {
		int orgListSize = dis.readInt();
		List<Organization> orgList = new ArrayList<>();
		for (int i = 0; i < orgListSize; i++) {
			String title = dis.readUTF();
			String link = isNull(dis.readUTF());
			int sizeOfJobList = dis.readInt();
			List<Organization.Job> jobList = new ArrayList<>();
			for (int j = 0; j < sizeOfJobList; j++) {
				jobList.add(new Organization.Job(
						LocalDate.parse(dis.readUTF(), FORMATTER),
						LocalDate.parse(dis.readUTF(), FORMATTER),
						dis.readUTF(), isNull(dis.readUTF())));
			}
			orgList.add(new Organization(new Link(title, link), jobList));
		}
		return new OrganizationText(orgList);
	}

	private String isNull(String f) {
		return f == null ? "" : f.equals("") ? null : f;
	}

}
