package com.crc.utils;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.imageio.ImageIO;

import com.crc.constants.Constants;
import com.crc.models.CoinDetails;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PreComputeUtils {

	static OkHttpClient client = new OkHttpClient();
	public static Map<String, CoinDetails> coinDetailsMap = new HashMap<>();

	// initialize resources at startup
	static {
		preComputeCoinList();
	}
	/**
	 * Pre populate coin list map, mapping symbol to id, for future calls
	 */
	private static void preComputeCoinList() {
		System.out.println("Getting data from service : preComputeCoinList()");
		Request request = new Request.Builder().url(Constants.CRYPTO_COMPARE_COIN_LIST_URL).build();

		JsonParser parser = new JsonParser();

		try (Response response = client.newCall(request).execute()) {
			JsonObject o = parser.parse(response.body().string()).getAsJsonObject();
			JsonObject dataNode = o.getAsJsonObject(Constants.DATA);
			Set<Entry<String, JsonElement>> entrySet = dataNode.entrySet();
			Iterator<Entry<String, JsonElement>> itr = entrySet.iterator();

			while (itr.hasNext()) {
				try {
					CoinDetails coinDetails = new CoinDetails();
					Entry<String, JsonElement> entry = itr.next();
					JsonObject coinNode = entry.getValue().getAsJsonObject();
					coinDetails.setId(coinNode.get(Constants.ID) != null ? coinNode.get(Constants.ID).getAsString() : null);
					coinDetails.setSymbol(coinNode.get(Constants.SYMBOL) != null ? coinNode.get(Constants.SYMBOL).getAsString() : null);
					coinDetails.setName(coinNode.get(Constants.NAME) != null ? coinNode.get(Constants.NAME).getAsString() : null);
					coinDetails.setAlgorithm(
							coinNode.get(Constants.ALGORITHM) != null ? coinNode.get(Constants.ALGORITHM).getAsString() : null);
					coinDetails.setImageUrl(
							coinNode.get(Constants.IMAGEURL) != null ? Constants.CLOUDFRONT_BASE_PATH + coinDetails.getSymbol() + ".png" : Constants.CLOUDFRONT_BASE_PATH + "BTC.png");
					coinDetails.setProofType(
							coinNode.get(Constants.PROOFTYPE) != null ? coinNode.get(Constants.PROOFTYPE).getAsString() : null);
					coinDetails.setPreMined(coinNode.get(Constants.FULLYPREMINED) != null ? coinNode.get(Constants.FULLYPREMINED).getAsString().equals("1") ? true : false : false);
					coinDetails.setFullName(Constants.FULL_NAME != null ? coinNode.get(Constants.FULL_NAME).getAsString() : null);
					coinDetailsMap.put(coinDetails.getSymbol(), coinDetails);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Exception within preComputeCoinList() " + ex.getClass().getName());
		}
	}
	
	/**
	 * Create local images before uploading to cloudfront
	 * @param symbol
	 * @param imageUrl
	 */
	@SuppressWarnings("unused")
	private static void createLocalImages(String symbol, String imageUrl) {
		
		if(imageUrl == null)
			return;
		
		try {
			String fileUrl = "" + symbol + ".png";
	        Path path = Paths.get(fileUrl);
	        if(!Files.exists(path)) {
	        	
				URLConnection connection = new URL(imageUrl).openConnection();
				connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
				connection.connect();
		        BufferedImage img = ImageIO.read(connection.getInputStream());
	        	
	        	Path tempPath = Files.createFile(path);
	        	ImageIO.write(img, "png", tempPath.toFile());
	        }	        
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
}
