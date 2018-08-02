package ru.javawebinar.basejava.model;


import java.util.List;

public class StringListText extends Text {

	private List<String> stringList;

	public StringListText(List<String> stringList) {
		this.stringList = stringList;
	}

	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		for (String line: stringList) {
			stringBuilder.append("- ").append(line).append("\n");
		}
		return stringBuilder.toString();
	}

}
