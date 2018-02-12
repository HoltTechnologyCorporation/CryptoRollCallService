package com.crc.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.crc.models.CreateFolioRequest;
import com.crc.models.GetFolioRequest;
import com.crc.service.FolioServiceImpl;

@Path("/folio")
public class FolioResource {

	FolioServiceImpl folioService = new FolioServiceImpl();
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createFolio(CreateFolioRequest request) {
		return Response.ok(folioService.createFolio(request)).build();
	}
	

	@POST
	@Path("retrieve")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getFolio(GetFolioRequest request) {
		return Response.ok(folioService.getFolio(request)).build();
	}
}
