/**
 * com.urise.webapp.model.Resume class
 */
public class Resume implements Comparable<Resume> {


	// Unique identifier
	protected String uuid;


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


	public String getUuid() {
		return uuid;
	}


	public void setUuid(String uuid) {
		this.uuid = uuid;
	}


	@Override
	public String toString() {
		return uuid;
	}


	@Override
	public int compareTo(Resume resume) {
		return uuid.compareTo(resume.uuid);
//		int numberOfResume = Integer.parseInt(this.uuid.substring(4,5));
//		int numberOfCompareResume = Integer.parseInt(o.uuid.substring(4,5));
//		return numberOfResume - numberOfCompareResume;
	}
}
