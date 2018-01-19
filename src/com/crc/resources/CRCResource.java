package com.crc.resources;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.crc.service.CRCServiceImpl;

@Path("/ticker")
public class CRCResource {

	CRCServiceImpl crcService = new CRCServiceImpl();
	
	@Path("/list")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getTickers() throws IOException {
		return crcService.getTickersFromCache();
	}

	@Path("/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getTicker(@PathParam("id") String id) throws IOException {
		return crcService.getTickerFromCache(id);
	}
	
	@Path("/currency/{currency}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getCurrExchangeRates(@PathParam("currency") String currency) throws IOException {
		return crcService.getCurrExchangeRates(currency);
	}
}
