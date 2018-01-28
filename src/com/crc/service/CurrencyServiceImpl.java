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

public class CurrencyServiceImpl {

	static OkHttpClient client = new OkHttpClient();
	static LoadingCache<String, String> currencyExchangeCache = null;
	
	static {
		client = getAllTrustingClient(client);

		currencyExchangeCache = CacheBuilder.newBuilder().maximumSize(100)
				.expireAfterWrite(Constants.FIXER_EXCHANGE_TIMEOUT, TimeUnit.DAYS)
				.build(new CacheLoader<String, String>() {

					@Override
					public String load(String param) throws Exception {
						return getCurrencyExchangeRates(param);
					}
				});
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
	 * Returns exchange rates wrt to currency
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
	
	/**
	 * Trust all certs 
	 * @param client
	 * @return
	 */
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
