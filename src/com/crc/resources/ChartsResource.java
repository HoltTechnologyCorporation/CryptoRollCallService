package com.crc.resources;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.crc.constants.TimePeriodEnum;
import com.crc.service.ChartsServiceImpl;

@Path("/charts")
public class ChartsResource {

	ChartsServiceImpl chartsService = new ChartsServiceImpl();

	@Path("/home")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getHomeCharts() throws IOException {
		CacheControl cacheControl = new CacheControl();
		cacheControl.setMaxAge(60);
		return Response.ok(chartsService.getHomeChartsFromCache()).cacheControl(cacheControl).build();
	}
	
	@Path("/histo/{period}/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getHistoricalData(@PathParam("period") TimePeriodEnum period, @PathParam("id") String id) throws IOException {
		CacheControl cacheControl = new CacheControl();
		cacheControl.setMaxAge(300);
		return Response.ok(chartsService.getHistoFromCache(period.toString(), id)).cacheControl(cacheControl).build();
	}
}
