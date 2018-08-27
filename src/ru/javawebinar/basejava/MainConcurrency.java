package ru.javawebinar.basejava;



public class MainConcurrency {
	private static final int THREADS_NUMBER = 10000;
	private static int counter;
	private static final Object LOCK = new Object();


	public static void main(String[] args) throws InterruptedException {
		final MainConcurrency mainConcurrency = new MainConcurrency();

		Thread thread0 = new Thread(mainConcurrency::div);
		thread0.start();


		Thread thread1 = new Thread(mainConcurrency::inc);
		thread1.start();

	}


	private void div() {

		synchronized (this) {
			counter = 1 / 16;
			inc();
		}

	}

	private void inc() {

		synchronized (this) {
			counter++;
			div();
		}
	}
}
