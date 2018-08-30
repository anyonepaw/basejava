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

		final MainConcurrency classObject1 = new MainConcurrency("classObject1");
		final MainConcurrency classObject2 = new MainConcurrency("classObject2");


		new Thread(() -> {
			classObject1.interactWithOther(classObject2);
		}).start();

		new Thread(() -> {
			classObject2.interactWithOther(classObject1);
		}).start();

	}


	private void interactWithOther(MainConcurrency mainConcurrency) {
		synchronized (this) {
			System.out.println(this.name + " interactWithOther " + mainConcurrency.getName());
			double b = Math.atan2(6,7);
			mainConcurrency.doSmthBack(this);
		}

	}

	private void doSmthBack(MainConcurrency mainConcurrency) {
		synchronized (this) {
			System.out.println(this.name + " doSmthBack " + mainConcurrency.getName());
		}
	}


}
