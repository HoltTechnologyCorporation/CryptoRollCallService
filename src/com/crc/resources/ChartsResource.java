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

import com.crc.constants.TimePeriodEnum;
import com.crc.models.ChartDataContainer;
import com.crc.models.HistoChartContainer;
import com.crc.service.ChartsServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(value = "Charts")
@Path("/charts")
public class ChartsResource {

	ChartsServiceImpl chartsService = new ChartsServiceImpl();

	@ApiOperation(response = ChartDataContainer.class, notes = "Cache Refresh Interval = 3 Minutes", httpMethod = HttpMethod.GET, protocols = "http", produces = "application/json", value = "Retrieves the chart data for the top performing coins in the previous hour, previous day and previous week.")
	@Path("/home")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getHomeCharts() throws IOException {
		CacheControl cacheControl = new CacheControl();
		cacheControl.setMaxAge(60);
		return Response.ok(chartsService.getHomeChartsFromCache()).cacheControl(cacheControl).build();
	}
	
	@ApiOperation(response = HistoChartContainer.class, notes = "Cache Refresh Interval = 15 Minutes", httpMethod = HttpMethod.GET, protocols = "http", produces = "application/json", value = "Retrieves the chart data for the top performing coins in the previous hour, previous day and previous week.")
	@Path("/histo/{period}/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getHistoricalData(@ApiParam(name = "period", required = true, value = "Time period for historical coin performance.", allowableValues = "DAY, WEEK, MONTH, THREE_MONTHS, SIX_MONTHS, YEAR") @PathParam("period") TimePeriodEnum period, @ApiParam(name = "id", value = "Id of the crypto currency in CAPS. Example : ETH") @PathParam("id") String id) throws IOException {
		CacheControl cacheControl = new CacheControl();
		cacheControl.setMaxAge(300);
		return Response.ok(chartsService.getHistoFromCache(period.toString(), id)).cacheControl(cacheControl).build();
	}
}
