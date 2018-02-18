package com.crc.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.Formatter;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

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

public class ChartsServiceImpl {
	
	static OkHttpClient client = new OkHttpClient();
	static LoadingCache<String, String> histoCache = null;

	/**
	 * init cache
	 */
	static {

		histoCache = CacheBuilder.newBuilder().maximumSize(7500)
				.expireAfterWrite(15, TimeUnit.MINUTES).build(new CacheLoader<String, String>() {

					@Override
					public String load(String param) throws Exception {
						String[] arr = param.split("-");
						return getHistoricalData(arr[0], arr[1]);
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
			String tickers = TickerServiceImpl.tickersCache.get(Constants.TICKERS);
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
	 * Get historical data from cache
	 * @param period
	 * @param id
	 * @return
	 */
	public String getHistoFromCache(String period, String id) {
		String result = null;
		try {
			result = histoCache.get(period + "-" + id);
		} catch (ExecutionException ex) {
			System.out.println("Exception within getHistoFromCache() " + ex.getClass().getName());
		}
		return result;
	}
	
	
	
	/**
	 * Get historical data for specified currency for a specified period
	 * @param period
	 * @return
	 */
	private static String getHistoricalData(String period, String id) {
		System.out.println("Getting data from service : getHistoricalData() " + period + " - Current Req Count= " + Constants.REQ_COUNT++);
		String result = null;
		Formatter formatter = new Formatter();
		Request request = new Request.Builder().url(formatter.format(Constants.COINCAP_HISTO_URL, period, id).toString()).build();
		try (Response response = client.newCall(request).execute()) {
			result = response.body().string();
		} catch (Exception ex) {
			System.out.println("Exception within getHistoricalData() " + ex.getClass().getName());
		} finally {
			formatter.close();
		}
		return result;
	}
}
