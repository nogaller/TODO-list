package de.nogaller.rest;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TaskTest {

	@Test
	public void toStringEmpty() throws Exception {
		Task task = new Task();
		assertEquals("{\"\",\"\"}", task.toString());
	}

	@Test
	public void toStringFull() throws Exception {
		Task task = new Task("some text", "2020-10-20");
		assertEquals("{\"some text\",\"2020-10-20\"}", task.toString());
	}
}
