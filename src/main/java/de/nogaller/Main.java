package de.nogaller;

import static org.glassfish.grizzly.utils.Charsets.UTF8_CHARSET;

import java.io.IOException;
import java.net.URI;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.ServerConfiguration;
import org.glassfish.grizzly.http.server.StaticHttpHandler;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.message.DeflateEncoder;
import org.glassfish.jersey.message.GZipEncoder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.filter.EncodingFilter;

/**
 * Main class.
 *
 */
public class Main {
	// Base URI the Grizzly HTTP server will listen on
	public static final String BASE_URI = "http://localhost:8080/tasks/";

	/**
	 * Starts Grizzly HTTP server exposing JAX-RS resources defined in this
	 * application.
	 *
	 * @return Grizzly HTTP server.
	 * @throws IOException
	 * @throws SecurityException
	 */
	public static HttpServer startServer() throws SecurityException, IOException {
		LogManager.getLogManager().readConfiguration(Main.class.getResourceAsStream("/all.properties"));

		// create a resource config that scans for JAX-RS resources and providers
		// in de.nogaller.rest package
		final ResourceConfig rc = new ResourceConfig().packages("de.nogaller.rest");
		// This will disable WADL creation
		rc.property(ServerProperties.WADL_FEATURE_DISABLE, true);

		// FIXME enable compressed transfer
//		enableCompression(rc);

		// create and start a new instance of grizzly http server
		// exposing the Jersey application at BASE_URI
		HttpServer server = GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);

		/* Set transfer protocol to UTF-8 */
		ServerConfiguration config = server.getServerConfiguration();
		config.setDefaultQueryEncoding(UTF8_CHARSET);

		/* Serve static HTMLs from "web" folder */
		String staticWebPath = Main.class.getClassLoader().getResource("web").getPath();
		Logger.getLogger(Main.class.getName()).fine("Serving static content from: " + staticWebPath);
		StaticHttpHandler httpHandler = new StaticHttpHandler(staticWebPath);
		httpHandler.setRequestURIEncoding(UTF8_CHARSET);
		server.getServerConfiguration().addHttpHandler(httpHandler, "/");

		return server;
	}

	private static void enableCompression(ResourceConfig rc) {
		rc.registerClasses(EncodingFilter.class, GZipEncoder.class, DeflateEncoder.class);
	}

	/**
	 * Main method.
	 *
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		final HttpServer server = startServer();
		System.out.println(String.format(
				"Jersey app started with WADL available at %sapplication.wadl\nHit enter to stop it...", BASE_URI));
		System.in.read();
		server.shutdownNow();
	}
}
