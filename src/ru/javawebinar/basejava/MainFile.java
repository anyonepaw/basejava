package ru.javawebinar.basejava;

import java.io.*;
import java.util.Objects;

public class MainFile {
	public static void main(String[] args) {
//		String pathName = "./.gitignore";
//		System.out.println(pathName);
//		File file = new File(pathName);
//		try {
//			System.out.println(file.getCanonicalPath());
//		} catch (IOException e) {
//			throw new RuntimeException("Error", e);
//		}
		File dir = new File("./src/ru/javawebinar/");
//		System.out.println(dir.isDirectory());
//		String[] list = dir.list();
//		if (list != null) {
//			for (String name : list) {
//				System.out.println(name);
//			}
//		}
//
//
//		try (FileInputStream fis = new FileInputStream(pathName)) {
//			System.out.println(fis.read());
//		} catch (IOException e) {
//			throw new RuntimeException(e);
//		}

		printDirectoryDeeply(dir, "");
	}


	public static void printDirectoryDeeply(File dir, String indent) {
		File[] files = dir.listFiles();
		for (File file : Objects.requireNonNull(files)) {
			if (file.isDirectory()) {
				System.out.println(indent + "Directory:" + file.getName());
				printDirectoryDeeply(file, indent + " ");
			}
		}
	}

}

