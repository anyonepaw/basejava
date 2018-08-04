package ru.javawebinar.basejava.model;

import java.util.List;

public class OrganizationText extends Text {

	private List<String> orgList;

	public OrganizationText(List<String> list) {
		this.orgList = list;
	}

	public String toString() {
		return String.join("\n", orgList);
	}

}
