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

public class TasksResourceTest {
	private static final String PATH = "tasks";
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

	private WebTarget path() {
		return target.path(PATH);
	}

	@Test
	public void addNewTask() throws Exception {
		/* get list */
		String out = path().request().get(String.class);
		assertEquals("[]", out);

		/* add item */
		out = path().queryParam("text", "first").request().put(Entity.text("text")).readEntity(String.class);
		assertEquals("1", out);

		/* add item */
		out = path().queryParam("text", "second").request().put(Entity.text("text")).readEntity(String.class);
		assertEquals("2", out);

		out = path().request().get(String.class);
		assertEquals("[\"first\",\"second\"]", out);
	}

	@Test
	public void modifyTask() throws Exception {
		addNewTask();

		/* update */
		String out = target.path(PATH + "/1").queryParam("text", "third").request().post(Entity.text("text"))
				.readEntity(String.class);
		assertEquals("second", out);

		/* getTaskInformation */
		out = target.path(PATH + "/1").request().get(String.class);
		assertEquals("third", out);

		out = path().request().get(String.class);
		assertEquals("[\"first\",\"third\"]", out);
	}

	@Test
	public void deleteTask() throws Exception {
		addNewTask();

		/* delete */
		String out = target.path(PATH + "/0").request().delete(String.class);
		assertEquals("first", out);

		out = path().request().get(String.class);
		assertEquals("[\"second\"]", out);
	}

}
