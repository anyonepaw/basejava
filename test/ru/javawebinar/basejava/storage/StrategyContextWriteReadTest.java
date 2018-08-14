package ru.javawebinar.basejava.storage;

import org.junit.Test;

import static ru.javawebinar.basejava.storage.AbstractStorageTest.STORAGE_DIR;


public class StrategyContextWriteReadTest{

	private StrategyWriteRead strategy;

	public StrategyContextWriteReadTest() {
		this.strategy = new ObjectStreamStorage(STORAGE_DIR);
		//this.strategy = new ObjectStreamStorage(STORAGE_DIR);
	}

	@Test
	public void setStrategy() {
	}

	@Test
	public void doWrite() {
	}

	@Test
	public void doRead() {
	}
}