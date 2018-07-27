package ru.javawebinar.basejava.model;

import java.util.*;

public class EnumeratedText extends Text {

	private Map<String, Map<String, String>> enumeratedText = new LinkedHashMap<>();

	@Override
	public void addContent(String title, String... text) {
		Map<String, String> content = new LinkedHashMap<>();

		List<String> list = Arrays.asList(text);
		Iterator<String> iterator = list.iterator();
		while(iterator.hasNext()){
			content.put(iterator.next(), iterator.next());
		}
		enumeratedText.put(title, content);
	}


	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		for (Map.Entry<String, Map<String, String>> line : enumeratedText.entrySet()) {
			stringBuilder.append(line.getKey()).append("\n");
			for (Map.Entry<String, String> line1 : line.getValue().entrySet()) {
				stringBuilder.append(line1.getKey()).append(" ").append(line1.getValue()).append("\n");
			}
		}
		return stringBuilder.toString();
	}


}

//Java Online Projects
//10/2013 - Сейчас	Автор проекта.
//                  Создание, организация и проведение Java онлайн проектов и стажировок.