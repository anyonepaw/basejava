package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BulletedText extends Text {

	private List<String> bulletedText = new ArrayList<>();

	@Override
	public void addContent(String title, String... text) {
		bulletedText.addAll(Arrays.asList(text));
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		for (String line: bulletedText) {
			stringBuilder.append("- ").append(line).append("\n");
		}
		return stringBuilder.toString();
	}

}
