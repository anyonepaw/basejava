package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.Resume;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainReflection {
	public static void main(String[] args) throws IllegalAccessException {
		Resume resume = new Resume();
		Field field = resume.getClass().getDeclaredFields()[0];
	 	field.setAccessible(true);
		System.out.println(field.getName());
		System.out.println(field.get(resume));
		System.out.println(resume.toString());
		field.set(resume, "new_uuid");
		System.out.println(resume);

		getMethod(resume);
	}

	public static void getMethod(Resume resume) {
		Method method[] = resume.getClass().getMethods();
		for (Method m: method) {
			System.out.println(m);
		}
		Method toStringOfResume = method[1];
		try {
			System.out.println(toStringOfResume.invoke(resume));
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}

	}

}
