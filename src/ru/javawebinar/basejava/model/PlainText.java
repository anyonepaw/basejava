package ru.javawebinar.basejava.model;

public class PlainText extends Text {

	private String plainText;

	public PlainText(String plainText) {
		this.plainText = plainText;
	}

	public String toString() {
		return plainText;
	}

}
