package ru.javawebinar.basejava.model;

import ru.javawebinar.basejava.util.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static ru.javawebinar.basejava.util.DateUtil.*;

@XmlAccessorType(XmlAccessType.FIELD)
public class Organization implements Serializable {

	private static final long serialVersionUID = 1L;

	private Link homePage;
	private List<Job> jobList;

	public Organization() {
	}

	public Organization(String name, String url, Job... jobs) {
		this(new Link(name, url), Arrays.asList(jobs));
	}

	public Organization(Link homePage, List<Job> jobList) {
		this.homePage = homePage;
		this.jobList = jobList;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Organization that = (Organization) o;

		if (homePage != null ? !homePage.equals(that.homePage) : that.homePage != null) return false;
		return jobList != null ? jobList.equals(that.jobList) : that.jobList == null;
	}

	@Override
	public int hashCode() {
		int result = homePage != null ? homePage.hashCode() : 0;
		result = 31 * result + (jobList != null ? jobList.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return homePage.toString();
	}

	public List<Job> getJobList() {
		return jobList;
	}

	public Link getHomePage() {
		return homePage;
	}

	@XmlAccessorType(XmlAccessType.FIELD)
	public static class Job implements Serializable {

		@XmlJavaTypeAdapter(LocalDateAdapter.class)
		private LocalDate fromDate;
		@XmlJavaTypeAdapter(LocalDateAdapter.class)
		private LocalDate toDate;
		private String title;
		private String description;

		public Job() {
		}

		public Job(LocalDate fromDate, LocalDate toDate, String title, String description) {
			Objects.requireNonNull(fromDate, "fromDate must not be null");
			Objects.requireNonNull(toDate, "toDate must not be null");
			Objects.requireNonNull(title, "title must not be null");
			this.fromDate = fromDate;
			this.toDate = toDate;
			this.title = title;
			this.description = (description == null) ? "" : description;
		}

		public Job(int fromYear, Month fromMonth, String title, String description) {
			this(of(fromYear, fromMonth), NOW, title, description);
		}

		public Job(int startYear, Month startMonth, int endYear, Month endMonth, String title, String description) {
			this(of(startYear, startMonth), of(endYear, endMonth), title, description);
		}

		public String getTitle() {
			return title;
		}

		public LocalDate getFromDate() {
			return fromDate;
		}

		public LocalDate getToDate() {
			return toDate;
		}

		public String getDescription() {
			return description;
		}

		@Override
		public String toString() {
			DateTimeFormatter df = DateTimeFormatter.ofPattern("MM/YYYY");
			return fromDate.format(df) + ' ' +
					toDate.format(df) + ' ' +
					title + ' ' +
					description;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;

			Job job = (Job) o;

			if (fromDate != null ? !fromDate.equals(job.fromDate) : job.fromDate != null) return false;
			if (toDate != null ? !toDate.equals(job.toDate) : job.toDate != null) return false;
			if (title != null ? !title.equals(job.title) : job.title != null) return false;
			return description != null ? description.equals(job.description) : job.description == null;
		}

		@Override
		public int hashCode() {
			int result = fromDate != null ? fromDate.hashCode() : 0;
			result = 31 * result + (toDate != null ? toDate.hashCode() : 0);
			result = 31 * result + (title != null ? title.hashCode() : 0);
			result = 31 * result + (description != null ? description.hashCode() : 0);
			return result;
		}
	}

	public void setHomePage(Link homePage) {
		this.homePage = homePage;
	}

	public void setJobList(List<Job> jobList) {
		this.jobList = jobList;
	}
}
