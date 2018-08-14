package ru.javawebinar.basejava.storage;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class ObjectStreamPathStorageTest extends AbstractStorageTest{

	public ObjectStreamPathStorageTest() {
		super(new ObjectStreamPathStorage((STORAGE_DIR).toPath()));
	}

	@Test
	public void doWrite() {
	}

	@Test
	public void doRead() {
	}
}