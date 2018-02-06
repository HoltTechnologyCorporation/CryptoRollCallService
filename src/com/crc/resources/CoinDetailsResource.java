package com.crc.resources;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.crc.models.CoinDetails;
import com.crc.service.CoinDetailsServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(value = "Coin Detail")
@Path("/coindetail")
public class CoinDetailsResource {

	CoinDetailsServiceImpl coinDetailsService = new CoinDetailsServiceImpl();

	@ApiOperation(notes = "Cache Refresh Interval = 12 Hours", response = CoinDetails.class, httpMethod = HttpMethod.GET, protocols = "http", produces = "application/json", value = "Retrieves all the details for the specified coin, including social links, source code repositories and available exchanges ")
	@Path("/{symbol}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCoinDetail(@ApiParam(name = "symbol", value = "The symbol of the coin for which the details need to be retrieved. Example = eth", required = true) @PathParam("symbol") String symbol) throws IOException {
		CacheControl cacheControl = new CacheControl();
		cacheControl.setMaxAge(86400);
		return Response.ok(coinDetailsService.getCoinDetailsFromCache(symbol.toUpperCase())).cacheControl(cacheControl).build();
	}
}
