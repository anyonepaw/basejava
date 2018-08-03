package ru.javawebinar.basejava.model;


import java.util.List;

public class StringListText extends Text {

	private List<String> stringList;

	public StringListText(List<String> stringList) {
		this.stringList = stringList;
	}

	public String toString() {
		return String.join("\n", stringList);
	}

}
