package com.crc.resources;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.crc.service.CRCServiceImpl;

@Path("/ticker")
public class CRCResource {

	CRCServiceImpl crcService = new CRCServiceImpl();
	
	@Path("/list")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTickers() throws IOException {
		CacheControl cacheControl = new CacheControl();
		cacheControl.setMaxAge(60);
		return Response.ok(crcService.getTickersFromCache()).cacheControl(cacheControl).build();
	}

	@Path("/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTicker(@PathParam("id") String id) throws IOException {
		CacheControl cacheControl = new CacheControl();
		cacheControl.setMaxAge(60);
		return Response.ok(crcService.getTickerFromCache(id)).cacheControl(cacheControl).build();
	}
	
	@Path("/currency/{currency}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCurrExchangeRates(@PathParam("currency") String currency) throws IOException {
		CacheControl cacheControl = new CacheControl();
		cacheControl.setMaxAge(43200);
		return Response.ok(crcService.getCurrExchangeRates(currency)).cacheControl(cacheControl).build();
	}
	
	@Path("/charts/home")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getHomeCharts() throws IOException {
		CacheControl cacheControl = new CacheControl();
		cacheControl.setMaxAge(60);
		return Response.ok(crcService.getHomeChartsFromCache()).cacheControl(cacheControl).build();
	}
}
