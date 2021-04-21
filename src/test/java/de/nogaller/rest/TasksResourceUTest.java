package de.nogaller.rest;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import jakarta.ws.rs.core.Response;

public class TasksResourceUTest {

	private TasksResource sut;

	@Before
	public void setUp() throws Exception {
		sut = new TasksResource();
	}

	@Test
	public void getTaskList() throws Exception {
		Response out = sut.getTaskList();
		Object text = out.getEntity();
		assertEquals("[]", text);
	}

	@Test
	public void addNewItem() throws Exception {
		Object out = sut.addNewTask("first").getEntity();
		assertEquals("1", out);

		out = sut.addNewTask("second").getEntity();
		assertEquals("2", out);

		out = sut.getTaskList().getEntity();
		assertEquals("[\"first\",\"second\"]", out);
	}

	@Test
	public void updateItem() throws Exception {
		addNewItem();

		// get text
		Object out = sut.getTaskInformation("1").getEntity();
		assertEquals("second", out);

		out = sut.modifyTask("1", "third").getEntity();
		assertEquals("second", out);

//		verify that value was updated
		out = sut.getTaskInformation("1").getEntity();
		assertEquals("third", out);
	}

	@Test
	public void deleteItem() throws Exception {
		addNewItem();

		Object out = sut.deleteTask("1").getEntity();
		assertEquals("second", out);

		out = sut.getTaskList().getEntity();
		assertEquals("[\"first\"]", out);
	}
}
