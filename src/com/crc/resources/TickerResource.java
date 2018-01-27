package com.crc.resources;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.crc.service.TickerServiceImpl;

@Path("/ticker")
public class TickerResource {

	TickerServiceImpl tickerService = new TickerServiceImpl();
	
	@Path("/list")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTickers() throws IOException {
		CacheControl cacheControl = new CacheControl();
		cacheControl.setMaxAge(60);
		return Response.ok(tickerService.getTickersFromCache()).cacheControl(cacheControl).build();
	}

	@Path("/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTicker(@PathParam("id") String id) throws IOException {
		CacheControl cacheControl = new CacheControl();
		cacheControl.setMaxAge(60);
		return Response.ok(tickerService.getTickerFromCache(id)).cacheControl(cacheControl).build();
	}
	
}
