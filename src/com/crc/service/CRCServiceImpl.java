package com.crc.service;

import java.security.cert.CertificateException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.crc.constants.Constants;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Loads data in cache with pre defined TTL
 * Service for getting price tickers for all currencies
 * Service for getting price tickers for single currency
 * @author shanganesh
 *
 */
public class CRCServiceImpl {

	static OkHttpClient client = new OkHttpClient();
	static LoadingCache<String, String> tickersCache = null;
	static LoadingCache<String, String> tickerCache = null;
	static LoadingCache<String, String> currencyExchangeCache = null;

	
	/**
	 * init cache
	 */
	static {
		
		client = getAllTrustingClient(client);
		
		tickersCache = CacheBuilder.newBuilder().maximumSize(10)
				.expireAfterWrite(Constants.TICKER_TIMEOUT, TimeUnit.MINUTES).build(new CacheLoader<String, String>() {
					
					@Override
					public String load(String param) throws Exception {
							return getTickers();
					}
				});
		
		tickerCache = CacheBuilder.newBuilder().maximumSize(2000)
				.expireAfterWrite(Constants.TICKER_TIMEOUT, TimeUnit.MINUTES).build(new CacheLoader<String, String>() {
					
					@Override
					public String load(String param) throws Exception {
							return getTicker(param);
					}
				});
		
		currencyExchangeCache = CacheBuilder.newBuilder().maximumSize(1000)
				.expireAfterWrite(Constants.FIXER_EXCHANGE_TIMEOUT, TimeUnit.DAYS).build(new CacheLoader<String, String>() {
					
					@Override
					public String load(String param) throws Exception {
							return getCurrencyExchangeRates(param);
					}
				});
	}
	
	/**
	 * Load tickers from cache. If not present, call underlying service
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
	
	public String getCurrExchangeRates(String currency) {
		String result = null;
		try {
			result = currencyExchangeCache.get(currency);
		} catch (ExecutionException ex) {
			System.out.println("Exception within getCurrExchangeRates() " + ex.getClass().getName());
		}
		return result;
	}
	
	/**
	 * Return all tickers
	 * @return
	 */
	private static String getTickers() {
		System.out.println("Getting data from service : getTickers()");
		String result = null;
		Request request = new Request.Builder().url(Constants.COINMARKETCAP_TICKER_URL).build();

		try (Response response = client.newCall(request).execute()) {
			result = response.body().string();
		} catch (Exception ex) {
			System.out.println("Exception within getTickers() " + ex.getClass().getName());
		}
		return result;
	}
	
	/**
	 * Return all tickers
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
	 * 
	 * @param id
	 * @return
	 */
	private static String getCurrencyExchangeRates(String currency) {
		System.out.println("Getting data from service : getCurrencyExchangeRates() " + currency);
		String result = null;
		Request request = new Request.Builder().url(Constants.FIXER_BASE_URL + currency).build();

		try (Response response = client.newCall(request).execute()) {
			result = response.body().string();
		} catch (Exception ex) {
			System.out.println("Exception within getCurrencyExchangeRates() " + ex.getClass().getName());
		}
		return result;
	}
	
	private static OkHttpClient getAllTrustingClient(OkHttpClient client) {
		OkHttpClient okHttpClient = null;
		try {
	        // Create a trust manager that does not validate certificate chains
	        final TrustManager[] trustAllCerts = new TrustManager[] {
	            new X509TrustManager() {
	              @Override
	              public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
	              }

	              @Override
	              public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
	              }

	              @Override
	              public java.security.cert.X509Certificate[] getAcceptedIssuers() {
	            	   return new java.security.cert.X509Certificate[0];
	              }
	            }
	        };

	        // Install the all-trusting trust manager
	        final SSLContext sslContext = SSLContext.getInstance("SSL");
	        sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
	        // Create an ssl socket factory with our all-trusting manager
	        final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
	        
	        okHttpClient = client.newBuilder().sslSocketFactory(sslSocketFactory, (X509TrustManager)trustAllCerts[0]).hostnameVerifier(new HostnameVerifier() {
	          @Override
	          public boolean verify(String hostname, SSLSession session) {
	            return true;
	          }
	        }).build();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return okHttpClient;
	}
}
