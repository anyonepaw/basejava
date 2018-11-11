package ru.javawebinar.basejava.util;

import org.junit.Assert;
import org.junit.Test;
import ru.javawebinar.basejava.model.PlainText;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.model.Text;

import static ru.javawebinar.basejava.TestData.*;

public class JsonParserTest {

	@Test
	public void testResume() throws Exception {
		String json = JsonParser.write(R1);
		System.out.println(json);
		Resume resume = JsonParser.read(json, Resume.class);
		Assert.assertEquals(R1, resume);
	}

	@Test
	public void write() throws Exception {
		Text text1 = new PlainText("Objective1");
		String json = JsonParser.write(text1, Text.class);
		System.out.println(json);
		Text text2 = JsonParser.read(json, Text.class);
		Assert.assertEquals(text1, text2);
	}

}