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
				dos.writeUTF(entry.getKey().name());
				dos.writeUTF(entry.getValue());
			}
			dos.writeInt(resume.getSections().size());
			for (Map.Entry<SectionType, Text> entry : resume.getSections().entrySet()) {
				SectionType sectionType = entry.getKey();
				dos.writeUTF(sectionType.name());
				switchEnums(sectionType, "doWrite", resume, null, dos, entry.getValue());
			}
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
				switchEnums(sectionType, "doRead", resume, dis, null, null);
			}
			return resume;
		}
	}


	private void switchEnums(SectionType sectionType, String method, Resume resume,
	                         DataInputStream dis, DataOutputStream dos, Text text) throws IOException {
		switch (sectionType) {
			case PERSONAL:
			case OBJECTIVE:
				if (method.equals("doWrite")) {
					dos.writeUTF(text.toString());
				} else if (method.equals("doRead")) {
					resume.addSection(sectionType, new PlainText(dis.readUTF()));
				}
				break;
			case ACHIEVEMENT:
			case QUALIFICATIONS:
				if (method.equals("doWrite")) {
					stringListTextWriter(dos, text);
				} else if (method.equals("doRead")) {
					stringListTextReader(sectionType, dis, resume);
				}
				break;
			case EDUCATION:
			case EXPERIENCE:
				if (method.equals("doWrite")) {
					organizationTextWriter(dos, text);
				} else if (method.equals("doRead")) {
					organizationTextReader(sectionType, dis, resume);
				}
				break;
		}
	}


	private void organizationTextReader(SectionType sectionType, DataInputStream dis, Resume resume) throws IOException {
		int orgListSize = dis.readInt();
		List<Organization> orgList = new ArrayList<>();
		for (int i = 0; i < orgListSize; i++) {
			String title = dis.readUTF();
			String link = (dis.readUTF());
			link = link.equals("null") ? null : link;
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
						jobTitle, jobDesc.equals("null") ? null : jobDesc));
			}
			orgList.add(new Organization(new Link(title, link), jobList));
		}
		resume.addSection(sectionType, new OrganizationText(orgList));
	}

	private void organizationTextWriter(DataOutputStream dos, Text text) throws IOException {
		List<Organization> orgList = ((OrganizationText) text).getOrgList();
		dos.writeInt(orgList.size());
		for (Organization organization : orgList) {
			dos.writeUTF(organization.getHomePage().getName());
			dos.writeUTF(Objects.toString(organization.getHomePage().getUrl(), "null"));
			List<Organization.Job> jobList = organization.getJobList();
			dos.writeInt(jobList.size());
			for (Organization.Job aJobList : jobList) {
				dos.writeUTF(aJobList.getFromDate().format(FORMATTER));
				dos.writeUTF(aJobList.getToDate().format(FORMATTER));
				dos.writeUTF(aJobList.getTitle());
				dos.writeUTF(Objects.toString(aJobList.getDescription(), "null"));
			}
		}
	}

	private void stringListTextReader(SectionType sectionType, DataInputStream dis, Resume resume) throws IOException{
		int stringListTextSize = dis.readInt();
		if (stringListTextSize != 0) {
			List<String> stringListText = new ArrayList<>();
			for (int j = 0; j < stringListTextSize; j++) {
				stringListText.add(dis.readUTF());
			}
			resume.addSection(sectionType, new StringListText(stringListText));
		} else {
			resume.addSection(sectionType, new StringListText(Collections.emptyList()));
		}
	}

	private void stringListTextWriter(DataOutputStream dos, Text text) throws IOException{
		List<String> stringListText = ((StringListText) text).getStringList();
		if (stringListText != null) {
			dos.writeInt(stringListText.size());
			for (String line : stringListText) {
				dos.writeUTF(line);
			}
		} else {
			dos.writeInt(0);
		}
	}
}
