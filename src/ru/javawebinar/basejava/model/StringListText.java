package ru.javawebinar.basejava.model;

import java.util.List;
import java.util.Objects;

public class StringListText extends Text {

	private List<String> stringList;

	public StringListText(List<String> stringList) {
		Objects.requireNonNull(stringList, "list must not be null");
		this.stringList = stringList;
	}

	@Override
	public String toString() {
		return String.join("\n", stringList);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		StringListText that = (StringListText) o;

		return stringList != null ? stringList.equals(that.stringList) : that.stringList == null;
	}

	@Override
	public int hashCode() {
		return stringList != null ? stringList.hashCode() : 0;
	}
}
