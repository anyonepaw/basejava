package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.List;

public class OrganizationText extends Text {

	private List<Organization> orgList;

	public OrganizationText(List<Organization> list) {
		this.orgList = list;
	}

	public String toString() {
		List<String> list = new ArrayList<>();
		for (Organization organization: orgList) {
			list.add(organization.toString());
		}
		return String.join("\n", list);
	}

}
