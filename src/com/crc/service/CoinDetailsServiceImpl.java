package com.crc.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.crc.constants.Constants;
import com.crc.models.CoinDetails;
import com.crc.utils.PreComputeUtils;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CoinDetailsServiceImpl {

	static OkHttpClient client = new OkHttpClient();
	static LoadingCache<String, CoinDetails> coinDetailsCache = null;

	// loading
	static {
		coinDetailsCache = CacheBuilder.newBuilder().maximumSize(2000)
				.expireAfterWrite(Constants.COIN_DETAIL_TIMEOUT, TimeUnit.HOURS).build(new CacheLoader<String, CoinDetails>() {

					@Override
					public CoinDetails load(String param) throws Exception {
						return getCoinDetails(param);
					}
				});
	}
	
	/**
	 * Get details for a coin
	 * 
	 * @param id
	 * @return
	 */
	public String getCoinDetailsFromCache(String symbol) {
		String result = null;
		
		if(!PreComputeUtils.coinDetailsMap.containsKey(symbol))
			return null;
		
		try {
			Gson gson = new Gson();
			result = gson.toJson(coinDetailsCache.get(symbol));
		} catch (ExecutionException ex) {
			System.out.println("Exception within getCoinDetailsFromCache() " + ex.getClass().getName());
		}
		return result;
	}
	
	/**
	 * Return coin details
	 * 
	 * @return
	 */
	private static CoinDetails getCoinDetails(String symbol) {
		System.out.println("Getting data from service : getCoinDetails()" + symbol);
		
		CoinDetails coinDetails = PreComputeUtils.coinDetailsMap.get(symbol.toUpperCase());
		if(coinDetails == null)
			return null;
		
		Request request = new Request.Builder().url(Constants.CRYPTO_COMPARE_SOCIAL + coinDetails.getId()).build();
		JsonParser parser = new JsonParser();

		try (Response response = client.newCall(request).execute()) {
			JsonObject o = parser.parse(response.body().string()).getAsJsonObject();
			JsonObject dataNode = o.getAsJsonObject(Constants.DATA);
			
			if(dataNode == null)
				return coinDetails;
			
			JsonObject twitterNode = dataNode.getAsJsonObject(Constants.TWITTER);

			if(twitterNode != null && twitterNode.get(Constants.LINK) != null)
				coinDetails.setTwitterUrl(twitterNode.get(Constants.LINK).getAsString());
			
			JsonObject redditNode = dataNode.getAsJsonObject(Constants.REDDIT);

			if(redditNode != null && redditNode.get(Constants.LINK) != null)
				coinDetails.setRedditUrl(redditNode.get(Constants.LINK).getAsString());

			JsonObject facebookNode = dataNode.getAsJsonObject(Constants.FACEBOOK);

			if(facebookNode != null && facebookNode.get(Constants.LINK) != null)
				coinDetails.setFacebookUrl(facebookNode.get(Constants.LINK).getAsString());
			
			JsonObject codeRepo = dataNode.getAsJsonObject(Constants.CODEREPOSITORY);
			List<String> repoList = null;
			if(codeRepo != null) {
				JsonArray codeRepoList = codeRepo.getAsJsonArray(Constants.LIST);
				if(codeRepoList != null && codeRepoList.size() > 0) {
					repoList = new ArrayList<>();
					for(int i=0; i<codeRepoList.size(); i++) {
						JsonObject codeRepoObj = codeRepoList.get(i).getAsJsonObject();
						repoList.add(codeRepoObj.get(Constants.URL).getAsString());
					}
				}
			}
			coinDetails.setCodeRepoLinks(repoList);
			
		} catch (Exception ex) {
			System.out.println("Exception within getCoinDetails() " + ex.getClass().getName());
		}
		return coinDetails;
	}	
}
