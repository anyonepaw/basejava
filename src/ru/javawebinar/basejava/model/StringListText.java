package ru.javawebinar.basejava.model;


import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class StringListText extends Text {

	private static final long serialVersionUID = 1L;

	private List<String> items;

	public StringListText(String... items) {
		this(Arrays.asList(items));
	}

	public StringListText() {
	}

	public StringListText(List<String> items) {
		Objects.requireNonNull(items, "list must not be null");
		this.items = items;
	}

	@Override
	public String toString() {
		return String.join("\n", items);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		StringListText that = (StringListText) o;

		return items != null ? items.equals(that.items) : that.items == null;
	}

	@Override
	public int hashCode() {
		return items != null ? items.hashCode() : 0;
	}

	public List<String> getItems() {
		return items;
	}
}
