package de.nogaller.rest;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;
import static jakarta.ws.rs.core.MediaType.TEXT_PLAIN;
import static jakarta.ws.rs.core.Response.ok;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import jakarta.inject.Singleton;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

@Path("/tasks")
@Singleton
public class TasksResource {

	private static final Logger logger = Logger.getLogger(TasksResource.class.getName());

	private List<String> tasks = new LinkedList<>();

	@GET
	@Produces(APPLICATION_JSON)
	public Response getTaskList() {
		StringBuilder out = new StringBuilder("[");
		if (!tasks.isEmpty()) {
			out.append('"');
			Iterator<String> iter = tasks.iterator();
			// first element
			out.append(iter.next());
			while (iter.hasNext()) {
				out.append("\",\"");
				out.append(iter.next());
			}
			out.append('"');
		}
		out.append("]");
		return ok(out.toString()).build();
	}

	@GET
	@Path("/{taskId}")
	@Produces(APPLICATION_JSON)
	public Response getTaskInformation(@PathParam("taskId") String taskId) {
		logger.finer(() -> "requesting details for: " + taskId);

		int nr = Integer.parseInt(taskId);
		return ok(tasks.get(nr)).build();
	}

	@POST
	@Path("/{taskId}")
	@Consumes(TEXT_PLAIN)
	@Produces(TEXT_PLAIN)
	public Response modifyTask(@PathParam("taskId") String taskId, @QueryParam("text") @DefaultValue("") String text) {
		logger.finest(() -> "Updading item " + taskId + " to: " + text);
		int nr = Integer.parseInt(taskId);
		String out = tasks.get(nr);
		tasks.set(nr, text);
		return ok(out).build();
	}

	@PUT
	@Produces(TEXT_PLAIN)
	public Response addNewTask(@QueryParam("text") @DefaultValue("") String text) {
		logger.finest(() -> "add new task: " + text);
		tasks.add(text);
		return ok(Integer.toString(tasks.size())).build();
	}

	@DELETE
	@Path("/{taskId}")
	@Produces(TEXT_PLAIN)
	public Response deleteTask(@PathParam("taskId") String taskId) {
		logger.finest(() -> "removing " + taskId);
		int nr = Integer.parseInt(taskId);
		String out = tasks.remove(nr);
		return ok(out).build();
	}
}