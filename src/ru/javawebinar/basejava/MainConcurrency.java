package ru.javawebinar.basejava;


public class MainConcurrency {

	private String name;

	public String getName() {
		return name;
	}

	public MainConcurrency(String name) {
		this.name = name;
	}

	public static void main(String[] args) {

		final String lock1 = "lock1";
		final String lock2 = "lock2";

		deadlock(lock1, lock2);
		deadlock(lock2, lock1);

	}

	private static void deadlock(Object lock1, Object lock2){
			new Thread(() -> {
				System.out.println("Waiting " + lock1);
				synchronized (lock1) {
					System.out.println("Holding " + lock1);
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("Waiting " + lock2);
					synchronized (lock2) {
						System.out.println("Holding " + lock2);
					}
				}

			}).start();
		}


//
//	private void interactWithOther(MainConcurrency mainConcurrency) {
//		synchronized (this) {
//			synchronized (this) {
//				System.out.println(this.name + " doSmthBack " + mainConcurrency.getName());
//			}
//		}
//
//	}



}
