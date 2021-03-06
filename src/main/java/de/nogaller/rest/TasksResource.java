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

@Path("/")
@Singleton
public class TasksResource {

	private static final Logger logger = Logger.getLogger(TasksResource.class.getName());

	/** Database stub */
	private final List<Task> tasks = new LinkedList<>();

	@GET
	@Produces(APPLICATION_JSON)
	public Response getTaskList() {
		StringBuilder out = new StringBuilder("[");
		if (!tasks.isEmpty()) {
			Iterator<Task> iter = tasks.iterator();
			// first element
			out.append(iter.next());
			while (iter.hasNext()) {
				out.append(",");
				out.append(iter.next());
			}
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
		return ok(tasks.get(nr).toString()).build();
	}

	@POST
	@Path("/{taskId}")
	@Consumes(TEXT_PLAIN)
	@Produces(APPLICATION_JSON)
	public Response modifyTask(@PathParam("taskId") String taskId, @QueryParam("text") @DefaultValue("") String text,
			@QueryParam("date") @DefaultValue("") String date) {
		logger.finest(() -> "Updading item " + taskId + " to: " + text + " @" + date);
		int nr = Integer.parseInt(taskId);
		Task out = tasks.get(nr);
		out.setText(text);
		out.setDueDate(date);
		return ok(out.toString()).build();
	}

	@PUT
	@Produces(TEXT_PLAIN)
	public Response addNewTask(@QueryParam("text") @DefaultValue("") String text) {
		logger.finest(() -> "add new task: " + text);
		tasks.add(new Task(text, ""));
		return ok(Integer.toString(tasks.size())).build();
	}

	@DELETE
	@Path("/{taskId}")
	@Produces(APPLICATION_JSON)
	public Response deleteTask(@PathParam("taskId") String taskId) {
		logger.finest(() -> "removing " + taskId);
		int nr = Integer.parseInt(taskId);
		Task out = tasks.remove(nr);
		return ok(out.toString()).build();
	}
}