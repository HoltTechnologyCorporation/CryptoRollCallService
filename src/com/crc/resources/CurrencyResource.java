package com.crc.resources;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.crc.service.CurrencyServiceImpl;

@Path("/currency")
public class CurrencyResource {

	CurrencyServiceImpl currencyService = new CurrencyServiceImpl();

	@Path("/{currency}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCurrExchangeRates(@PathParam("currency") String currency) throws IOException {
		CacheControl cacheControl = new CacheControl();
		cacheControl.setMaxAge(43200);
		return Response.ok(currencyService.getCurrExchangeRates(currency)).cacheControl(cacheControl).build();
	}
}
