package org.bitsteam.rest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/")
// Type in browser: http://localhost:9080/HelloLibertyWeb/hello/[method i.e. sayHello]
public class GreetingResource {
	@GET
	@Path("/sayHello")
	public String getMessage(){
		System.out.println("JAXRS - Greeting Service called...");
		return "Hello from your friendly Liberty RS Web Service";
	}
}
