package org.bitsteam.rest;

import javax.naming.Context;
import javax.ws.rs.GET;
import javax.ws.rs.Path;


@Path("/")

public class MessageSenderService {
	@GET
	@Path("blah")
	// http://localhost:9080/HelloLibertyWeb/hello/dropMessage
	public String putQMessage() {
		System.out.println("JAXRS - putQMessage Service called");
		
		return "Queue Message dropped";
	}

}
