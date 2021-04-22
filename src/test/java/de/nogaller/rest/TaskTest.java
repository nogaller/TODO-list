package de.nogaller.rest;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TaskTest {

	@Test
	public void toStringEmpty() throws Exception {
		Task task = new Task();
		assertEquals("{\"text\":\"\",\"date\":\"\"}", task.toString());
	}

	@Test
	public void toStringFull() throws Exception {
		Task task = new Task("some test", "2020-10-20");
		assertEquals("{\"text\":\"some test\",\"date\":\"2020-10-20\"}", task.toString());
	}
}
