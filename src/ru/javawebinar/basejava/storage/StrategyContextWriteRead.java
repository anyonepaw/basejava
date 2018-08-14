package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class StrategyContextWriteRead {

	private StrategyWriteRead strategy;

	protected StrategyContextWriteRead(StrategyWriteRead strategy) {
		this.strategy = strategy;
	}

	protected void doWrite(Resume r, OutputStream os) throws IOException{
		strategy.doWrite(r, os);
	}

	protected Resume doRead(InputStream is) throws IOException{
		return strategy.doRead(is);
	}

}
