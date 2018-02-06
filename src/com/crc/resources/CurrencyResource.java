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

import com.crc.models.CurrencyExchange;
import com.crc.service.CurrencyServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(value = "Currency")
@Path("/currency")
public class CurrencyResource {

	CurrencyServiceImpl currencyService = new CurrencyServiceImpl();

	@ApiOperation(response = CurrencyExchange.class , notes = "Cache Refresh Interval = 24 Hours", httpMethod = HttpMethod.GET, protocols = "http", produces = "application/json", value = "List of exchange rates based on the base currency")
	@Path("/{currency}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCurrExchangeRates(@ApiParam(name = "currency", value = "Base currency, based on which exchange rates are returned. Example = USD", required = true) @PathParam("currency") String currency) throws IOException {
		CacheControl cacheControl = new CacheControl();
		cacheControl.setMaxAge(43200);
		return Response.ok(currencyService.getCurrExchangeRates(currency)).cacheControl(cacheControl).build();
	}
}
