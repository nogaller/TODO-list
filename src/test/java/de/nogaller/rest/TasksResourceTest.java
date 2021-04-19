package de.nogaller.rest;

import static org.junit.Assert.assertEquals;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.nogaller.Main;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Response;

public class TasksResourceTest {
	private HttpServer server;
	private WebTarget target;

	@Before
	public void setUp() throws Exception {
		// start the server
		server = Main.startServer();
		// create the client
		Client c = ClientBuilder.newClient();

		// uncomment the following line if you want to enable
		// support for JSON in the client (you also have to uncomment
		// dependency on jersey-media-json module in pom.xml and Main.startServer())
		// --
		// c.configuration().enable(new
		// org.glassfish.jersey.media.json.JsonJaxbFeature());

		target = c.target(Main.BASE_URI);
	}

	@After
	public void tearDown() throws Exception {
		server.shutdownNow();
	}

	@Test
	public void getTaskList() {
		String responseMsg = target.path("tasks").request().get(String.class);
		assertEquals("111", responseMsg);
	}

	@Test
	public void getTaskInformation() {
		String responseMsg = target.path("tasks/info/321").request().get(String.class);
		assertEquals("222 321", responseMsg);
	}

	@Test
	public void modifyTask() throws Exception {
		Response responseMsg = target.path("tasks").request().post(Entity.text("321"));
		assertEquals("333", responseMsg.readEntity(String.class));
	}

	@Test
	public void addNewTask() throws Exception {
		Response responseMsg = target.path("tasks").request().put(Entity.text(""));
		assertEquals("444", responseMsg.readEntity(String.class));
	}

	@Test
	public void deleteTask() throws Exception {
		String responseMsg = target.path("tasks/321").request().delete(String.class);
		assertEquals("555 321", responseMsg);
	}
}
