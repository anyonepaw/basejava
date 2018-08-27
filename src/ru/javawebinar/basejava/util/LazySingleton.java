package ru.javawebinar.basejava.util;

public class LazySingleton {
	private static LazySingleton INSTANCE;

	private LazySingleton() {
	}

	private static class LazySingletonHolder{
		private static final LazySingleton INSTANCE = new LazySingleton();
	}

	public static LazySingleton getInstance() {
		return LazySingletonHolder.INSTANCE;
//		if (INSTANCE == null) {
//			synchronized (LazySingleton.class) {
//				INSTANCE = new LazySingleton();
//			}
//		}
//		return INSTANCE;
	}
//
}
