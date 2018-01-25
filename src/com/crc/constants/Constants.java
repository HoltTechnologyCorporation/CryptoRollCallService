package com.crc.constants;

public class Constants {

	public static final String TICKERS = "tickers";
	public static final String COINMARKETCAP_TICKER_URL = "https://api.coinmarketcap.com/v1/ticker/";
	public static final String COINMARKETCAP_ALL_TICKER_URL = "https://api.coinmarketcap.com/v1/ticker?limit=0";
	public static final String FIXER_BASE_URL = "https://api.fixer.io/latest?base=";
	public static final String COINCAP_HISTO_URL = "http://coincap.io/history/%s/%s";
	
	// cache related
	public static final Integer TICKER_TIMEOUT = 3;
	public static final Integer FIXER_EXCHANGE_TIMEOUT = 1;
	
}
