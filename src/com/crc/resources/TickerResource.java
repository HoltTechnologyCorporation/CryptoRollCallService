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

import com.crc.models.Ticker;
import com.crc.service.TickerServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Contact;
import io.swagger.annotations.Info;
import io.swagger.annotations.License;
import io.swagger.annotations.SwaggerDefinition;

@SwaggerDefinition(
		info = @Info(
				title = "CryptoRollCall - API (Alpha)", 
				license = @License(
						name = "MIT", 
						url = "https://opensource.org/licenses/MIT"), 
				description = "APIs that power CryptoRollCall - http://www.cryptorollcall.com"
						+ " The APIs are currently in Alpha phase. "
						+ "All the APIs are public and free for access. "
						+ "All responses are cached with different durations for cache-refresh."
						+ "Please feel free to use it and kindly don't abuse it."
						+ "Got feedback/suggestions/critique : Send an email to : cryptorollcall@gmail.com", 
				version = "v1.0", 
				contact = @Contact(
						name = "Developer", 
						email = "cryptorollcall@gmail.com", 
						url = "http://www.cryptorollcall.com")
				)
		)
@Api(value = "Ticker")
@Path("/ticker")
public class TickerResource {

	TickerServiceImpl tickerService = new TickerServiceImpl();

	@ApiOperation(notes = "Cache Refresh Interval = 3 Minutes", responseContainer = "List", response = Ticker.class, httpMethod = HttpMethod.GET, protocols = "http", produces = "application/json", value = "Retrieves the entire list of crypto currency tickers.")
	@Path("/list")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTickers() throws IOException {
		CacheControl cacheControl = new CacheControl();
		cacheControl.setMaxAge(60);
		return Response.ok(tickerService.getTickersFromCache()).cacheControl(cacheControl).build();
	}

	@ApiOperation(notes = "Cache Refresh Interval = 3 Minutes", response = Ticker.class, httpMethod = HttpMethod.GET, protocols = "http", produces = "application/json", value = "Retrieves the crypto currency ticker for the specified id.")
	@Path("/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTicker(
			@ApiParam(name = "id", required = true, value = "The id used for the crypto currency. Example - bitcoin") @PathParam("id") String id)
			throws IOException {
		CacheControl cacheControl = new CacheControl();
		cacheControl.setMaxAge(60);
		return Response.ok(tickerService.getTickerFromCache(id)).cacheControl(cacheControl).build();
	}

}
