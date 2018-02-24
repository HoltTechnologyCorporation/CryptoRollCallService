package com.crc.constants;

public class Constants {

	public static final String TICKERS = "tickers";
	public static final String COINMARKETCAP_TICKER_URL = "https://api.coinmarketcap.com/v1/ticker/";
	public static final String COINMARKETCAP_ALL_TICKER_URL = "https://api.coinmarketcap.com/v1/ticker?limit=0";
	public static final String FIXER_BASE_URL = "https://api.fixer.io/latest?base=";
	public static final String COINCAP_HISTO_URL = "http://coincap.io/history/%s/%s";
	public static final String CRYPTO_COMPARE_COIN_LIST_URL = "https://min-api.cryptocompare.com/data/all/coinlist";
	public static final String CRYPTO_COMPARE_SOCIAL = "https://www.cryptocompare.com/api/data/socialstats/?id=";
	public static final String CRYPTO_BASE_IMAGE_URL = "https://www.cryptocompare.com";
	public static final String CRYPTO_COMPARE_FULL = "https://www.cryptocompare.com/api/data/coinsnapshotfullbyid/?id=";
	// TODO : may need to change, once it moves to min api
	public static final String CRYPTO_COMPARE_COIN_SNAPSHOT = "https://www.cryptocompare.com/api/data/coinsnapshot/?fsym=~&tsym=BTC";

	// Cloudfront 
	public static final String CLOUDFRONT_BASE_PATH = "https://d4joa9r2yj65e.cloudfront.net/";
	
	// field names
	public static final String DATA = "Data";
	public static final String ID = "Id";
	public static final String SYMBOL = "Symbol";
	public static final String NAME = "Name";
	public static final String ALGORITHM = "Algorithm";
	public static final String IMAGEURL = "ImageUrl";
	public static final String PROOFTYPE = "ProofType";
	public static final String FULLYPREMINED = "FullyPremined";
	public static final String GENERAL = "General";
	public static final String AFFILIATE_URL = "AffiliateUrl";
	public static final String EXCHANGES = "Exchanges";
	public static final String MARKET = "MARKET";
	public static final String FULL_NAME = "FullName";
	
	// cache related
	public static final Integer TICKER_TIMEOUT = 3;
	public static final Integer COIN_DETAIL_TIMEOUT = 12;
	public static final Integer FIXER_EXCHANGE_TIMEOUT = 1;
	public static final String TWITTER = "Twitter";
	public static final String LINK = "link";
	public static final String REDDIT = "Reddit";
	public static final String FACEBOOK = "Facebook";
	public static final String CODEREPOSITORY = "CodeRepository";
	public static final String LIST = "List";
	public static final String URL = "url";

	// number of words in pass phrase
	public static final Integer DIFFICULTY = 3;

	// hash related
	public static final Integer ITERATIONS = 14000;
	public static final Integer KEY_LENGTH = 256;
	public static final String KEY_FACTORY = "PBKDF2WithHmacSHA1";
	public static final String SECURE_RANDOM_INSTANCE = "SHA1PRNG";
	public static final String STATIC_SALT_LOC = "/tmp/static_salt";
	
	// basic req counter
	public static Long REQ_COUNT = 0L;
	
	// seo
	public static String BASE_TITLE_SEO = "Live and historical prices, social links and more";
	public static String BASE_DESC_SEO = "Stay up to date with the latest price movements. Live and historical price charts, social links, supply, market cap and breaking news.";

}
