package de.nogaller.rest;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;
import static jakarta.ws.rs.core.MediaType.TEXT_PLAIN;
import static jakarta.ws.rs.core.Response.ok;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

@Path("/tasks")
public class TasksResource {

	@GET
	@Produces(APPLICATION_JSON)
	public Response getTaskList() {
		return ok("111").build();
	}

	@GET
	@Path("/info/{taskId}")
	@Produces(APPLICATION_JSON)
	public Response getTaskInformation(@PathParam("taskId") String taskId) {
		return ok("222 " + taskId).build();
	}

	@POST
	@Consumes(TEXT_PLAIN)
	@Produces(TEXT_PLAIN)
	public Response modifyTask() {
		return ok("333").build();
	}

	@PUT
	@Produces(TEXT_PLAIN)
	public Response addNewTask() {
		return ok("444").build();
	}

	@DELETE
	@Path("/{taskId}")
	@Produces(TEXT_PLAIN)
	public Response deleteTask(@PathParam("taskId") String taskId) {
		return ok("555 " + taskId).build();
	}
}