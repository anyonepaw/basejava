package ru.javawebinar.basejava.model;

public class PlainText extends Text {

	private String plainText;

	public PlainText(String plainText) {
		this.plainText = plainText;
	}

	@Override
	public String toString() {
		return plainText;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		PlainText plainText1 = (PlainText) o;

		return plainText != null ? plainText.equals(plainText1.plainText) : plainText1.plainText == null;
	}

	@Override
	public int hashCode() {
		return plainText != null ? plainText.hashCode() : 0;
	}
}
