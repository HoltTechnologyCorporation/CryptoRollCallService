package com.crc.service;

import java.security.cert.CertificateException;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.crc.constants.Constants;
import com.crc.models.ChartData;
import com.crc.models.ChartDataContainer;
import com.crc.models.Ticker;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Loads data in cache with pre defined TTL Service for getting price tickers
 * for all currencies Service for getting price tickers for single currency
 * 
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

		currencyExchangeCache = CacheBuilder.newBuilder().maximumSize(100)
				.expireAfterWrite(Constants.FIXER_EXCHANGE_TIMEOUT, TimeUnit.DAYS)
				.build(new CacheLoader<String, String>() {

					@Override
					public String load(String param) throws Exception {
						return getCurrencyExchangeRates(param);
					}
				});
	}

	/**
	 * Create charts required for home page
	 * 
	 * @return
	 */
	public String getHomeChartsFromCache() {
		String result = null;
		try {
			Gson gson = new Gson();
			String tickers = tickersCache.get(Constants.TICKERS);
			List<Ticker> items = gson.fromJson(tickers, new TypeToken<List<Ticker>>() {
			}.getType());

			// hourly
			Collections.sort(items, new Comparator<Ticker>() {
				public int compare(Ticker x, Ticker y) {

					if(x.getPercent_change_1h() == y.getPercent_change_1h())
						return 0;
					else if(x.getPercent_change_1h() < y.getPercent_change_1h())
						return 1;
					else
						return -1;
				}
			});

			List<ChartData> hourlyList = new LinkedList<>();

			for (int i = 0; i < 5; i++) {

				ChartData data = new ChartData(items.get(i).getName(), items.get(i).getPercent_change_1h());
				hourlyList.add(data);
			}

			// daily
			Collections.sort(items, new Comparator<Ticker>() {
				public int compare(Ticker x, Ticker y) {

					if(x.getPercent_change_24h() == y.getPercent_change_24h())
						return 0;
					else if(x.getPercent_change_24h() < y.getPercent_change_24h())
						return 1;
					else
						return -1;
				}
			});

			List<ChartData> dailyList = new LinkedList<>();

			for (int i = 0; i < 5; i++) {

				ChartData data = new ChartData(items.get(i).getName(), items.get(i).getPercent_change_24h());
				dailyList.add(data);
			}

			// weekly
			Collections.sort(items, new Comparator<Ticker>() {
				public int compare(Ticker x, Ticker y) {

					if(x.getPercent_change_7d() == y.getPercent_change_7d())
						return 0;
					else if(x.getPercent_change_7d() < y.getPercent_change_7d())
						return 1;
					else
						return -1;
				}
			});

			List<ChartData> weeklyList = new LinkedList<>();

			for (int i = 0; i < 5; i++) {

				ChartData data = new ChartData(items.get(i).getName(), items.get(i).getPercent_change_7d());
				weeklyList.add(data);
			}

			ChartDataContainer container = new ChartDataContainer(hourlyList, dailyList, weeklyList);
			result = gson.toJson(container);

		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Exception within getHomeChartsFromCache() " + ex.getClass().getName());
		}
		return result;
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
	 * Return all tickers
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
			final TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
				@Override
				public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType)
						throws CertificateException {
				}

				@Override
				public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType)
						throws CertificateException {
				}

				@Override
				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					return new java.security.cert.X509Certificate[0];
				}
			} };

			// Install the all-trusting trust manager
			final SSLContext sslContext = SSLContext.getInstance("SSL");
			sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
			// Create an ssl socket factory with our all-trusting manager
			final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

			okHttpClient = client.newBuilder().sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0])
					.hostnameVerifier(new HostnameVerifier() {
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
