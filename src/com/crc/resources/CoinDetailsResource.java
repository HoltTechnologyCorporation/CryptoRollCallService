package com.crc.resources;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.crc.service.CoinDetailsServiceImpl;

@Path("/coindetail")
public class CoinDetailsResource {

	CoinDetailsServiceImpl coinDetailsService = new CoinDetailsServiceImpl();

	@Path("/{symbol}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getHomeCharts(@PathParam("symbol") String symbol) throws IOException {
		CacheControl cacheControl = new CacheControl();
		cacheControl.setMaxAge(86400);
		return Response.ok(coinDetailsService.getCoinDetailsFromCache(symbol.toUpperCase())).cacheControl(cacheControl).build();
	}
}
