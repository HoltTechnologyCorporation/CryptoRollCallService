package com.crc.service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.crc.constants.Constants;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TickerServiceImpl {

	static OkHttpClient client = new OkHttpClient();
	static LoadingCache<String, String> tickersCache = null;
	static LoadingCache<String, String> tickerCache = null;
	
	static {
		
		tickersCache = CacheBuilder.newBuilder().maximumSize(5)
				.expireAfterWrite(Constants.TICKER_TIMEOUT, TimeUnit.MINUTES).build(new CacheLoader<String, String>() {

					@Override
					public String load(String param) throws Exception {
						return getTickers();
					}
				});

		tickerCache = CacheBuilder.newBuilder().maximumSize(1500)
				.expireAfterWrite(Constants.TICKER_TIMEOUT, TimeUnit.MINUTES).build(new CacheLoader<String, String>() {

					@Override
					public String load(String param) throws Exception {
						return getTicker(param);
					}
				});
	}
	
	/**
	 * Load tickers from cache. If not present, call underlying service
	 * 
	 * @return
	 */
	public String getTickersFromCache() {
		String result = null;
		try {
			result = tickersCache.get(Constants.TICKERS);
		} catch (ExecutionException ex) {
			System.out.println("Exception within getTickersFromCache() " + ex.getClass().getName());
		}
		return result;
	}

	/**
	 * Get price ticker for a single currency
	 * 
	 * @param id
	 * @return
	 */
	public String getTickerFromCache(String id) {
		String result = null;
		try {
			result = tickerCache.get(id);
		} catch (ExecutionException ex) {
			System.out.println("Exception within getTickerFromCache() " + ex.getClass().getName());
		}
		return result;
	}
	
	/**
	 * Return all tickers
	 * 
	 * @return
	 */
	private static String getTickers() {
		System.out.println("Getting data from service : getTickers()");
		String result = null;
		Request request = new Request.Builder().url(Constants.COINMARKETCAP_ALL_TICKER_URL).build();

		try (Response response = client.newCall(request).execute()) {
			result = response.body().string();
		} catch (Exception ex) {
			System.out.println("Exception within getTickers() " + ex.getClass().getName());
		}
		return result;
	}

	/**
	 * Return ticker for specified coin
	 * 
	 * @return
	 */
	private static String getTicker(String id) {
		System.out.println("Getting data from service : getTicker() " + id);
		String result = null;
		Request request = new Request.Builder().url(Constants.COINMARKETCAP_TICKER_URL + id).build();

		try (Response response = client.newCall(request).execute()) {
			result = response.body().string();
		} catch (Exception ex) {
			System.out.println("Exception within getTicker() " + ex.getClass().getName());
		}
		return result;
	}
	
	
	/**
	 * Utility method to print options
	 * TODO: Need to store it in DB
	 * @param result
	 */
	@SuppressWarnings("unused")
	private void printOptions(String result) {

		JsonParser parser = new JsonParser();
		JsonArray arr = parser.parse(result).getAsJsonArray();
		for(int i=0; i<arr.size(); i++) {
			JsonObject obj = arr.get(i).getAsJsonObject();
			System.out.println("<option value =\"" + obj.get("id").getAsString().replaceAll(" ", "-").toLowerCase() + "~" + obj.get("symbol").getAsString() + "\">");
			System.out.println(obj.get("name").getAsString());
			System.out.println("</option>");
		}
	}
}
