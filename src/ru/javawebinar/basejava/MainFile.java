package ru.javawebinar.basejava;

import java.io.*;
import java.util.Objects;

public class MainFile {
	public static void main(String[] args) {
		String pathName = "./.gitignore";
		System.out.println(pathName);
		File file = new File(pathName);
		try {
			System.out.println(file.getCanonicalPath());
		} catch (IOException e) {
			throw new RuntimeException("Error", e);
		}
		File dir = new File("./src/ru/javawebinar/basejava/");
		System.out.println(dir.isDirectory());
		String[] list = dir.list();
		if (list != null) {
			for (String name : list) {
				System.out.println(name);
			}
		}


		try (FileInputStream fis = new FileInputStream(pathName)) {
			System.out.println(fis.read());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}


		printFileNames("./src/ru/javawebinar/basejava/");
	}


	public static void printFileNames(String fileName){
		File[] files = new File(fileName).listFiles();

		for (File file: Objects.requireNonNull(files)){
			System.out.println(file.getAbsolutePath());
			if(file.isDirectory()){
				printFileNames(file.getAbsolutePath());
			}
		}

	}
}
