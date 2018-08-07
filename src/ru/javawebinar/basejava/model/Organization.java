package ru.javawebinar.basejava.model;

import java.util.List;

public class Organization {

	private Link homepage;
	private List<Job> jobList;

	public Organization(String name, String url, List<Job> jobList) {
		this.homepage = new Link(name, url);
		this.jobList = jobList;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Organization that = (Organization) o;

		if (homepage != null ? !homepage.equals(that.homepage) : that.homepage != null) return false;
		return jobList != null ? jobList.equals(that.jobList) : that.jobList == null;
	}

	@Override
	public int hashCode() {
		int result = homepage != null ? homepage.hashCode() : 0;
		result = 31 * result + (jobList != null ? jobList.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(homepage.getName()).append("\n");
		for (Job job : jobList) {
			sb.append(job.toString());
		}
		return sb.toString();
	}
}
