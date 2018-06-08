package ru.javawebinar.basejava;

public class MainString {
	public static void main(String[] args) {
		String[] strings = new String[]{"1", "2", "3", "4", "5"};
		StringBuilder sb = new StringBuilder();
		for (String string: strings){
			sb.append(string).append(", ");
		}
		System.out.println(sb);

		String st1 = "abc";
		String st2 = "abc";
		System.out.println(st1 == st2);


		String hello = "Hello";
		String habr = "habrahabr";
		String delimiter = ", ";
		System.out.println(String.join(delimiter, hello, habr));

	}

}
