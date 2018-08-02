package ru.javawebinar.basejava.model;

public class PlainText extends Text {

	private String plainText;

	public PlainText(String plainText) {
		this.plainText = plainText;
	}

	public PlainText(String ... array) {
		StringBuilder sb = new StringBuilder();
		plainText = sb.append(array[0]).append("\n").append(array[1]).append(" ").append(array[2]).toString();
	}

	public String toString() {
		return plainText;
	}

}
