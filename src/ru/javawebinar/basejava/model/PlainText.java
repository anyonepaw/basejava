package ru.javawebinar.basejava.model;


public class PlainText extends Text {

	private static final long serialVersionUID = 1L;

	private String content;

	public PlainText() {
	}

	public PlainText(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return content;
	}

	public String getContent(){
		return content;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		PlainText plainText1 = (PlainText) o;

		return content != null ? content.equals(plainText1.content) : plainText1.content == null;
	}

	@Override
	public int hashCode() {
		return content != null ? content.hashCode() : 0;
	}
}
