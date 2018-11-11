package ru.javawebinar.basejava.model;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class OrganizationText extends Text {

	private static final long serialVersionUID = 1L;



	private List<Organization> orgList;

	public OrganizationText() {
	}

	public OrganizationText(List<Organization> list) {
		Objects.requireNonNull(list, "organization list must not be null");
		this.orgList = list;
	}

	public OrganizationText(Organization ... organizations) {
		this(Arrays.asList(organizations));
	}

	public String toString() {
		List<String> list = new ArrayList<>();
		for (Organization organization: orgList) {
			list.add(organization.toString());
		}
		return String.join("", list);
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		OrganizationText that = (OrganizationText) o;

		return orgList != null ? orgList.equals(that.orgList) : that.orgList == null;
	}

	@Override
	public int hashCode() {
		return orgList != null ? orgList.hashCode() : 0;
	}

	public List<Organization> getOrgList() {
		return orgList;
	}

	public void setOrgList(List<Organization> orgList) {
		this.orgList = orgList;
	}
}
