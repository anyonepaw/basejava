package ru.javawebinar.basejava.model;

public class PlainText extends Text {

	private String plainText;

	@Override
	public void addContent(String title, String... text) {
		StringBuilder stringBuilder = new StringBuilder();
		for (String line : text) {
			plainText = stringBuilder.append(line).append("\n").toString();
		}
	}

	@Override
	public String toString() {
		return plainText;
	}

}
