module de.nogaller {
	// logging modules
	requires java.logging;
	requires grizzly.http.server;
	requires jersey.server;
	requires jersey.common;
	requires jersey.container.grizzly2.http;
	requires grizzly.framework;
	requires jakarta.ws.rs;
	requires jakarta.inject;
}