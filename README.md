# CryptoRollCallService

Services that power http://www.cryptorollcall.com

**Features** :-
- Services implemented and exposed as RESTful APIs
    - Price ticker for all crypto currencies (GET - /ticker/list)
    - Price ticker for a single crypto currency (GET - /ticker/{currencyName}
    - Top 5 gainers chart across all crypto currencies for 1 hour, 1 day and 1 week (GET - /ticker/home/charts)
    - Currency exchange rates information (GET - /ticker/currency/{currencyName}
    
**Details** :-
- All services go through Caching layer implemented using Guava, with varying expireAfterWrite times
- All services also have a cache-control header returned in response, for browser side caching, when applicable.
- RESTful services implemented using Jersey2

**Upcoming features** :-
- User signup feature, so that portfolio is not device/browser specific
    - Current implementation uses local storage for storing user portfolio information. Hence, it is tightly coupled with           browser/device. 
      This feature would involve asking the user to sign up, storing the user name/ password (salted + hashed) in a database,
      Storing the corresponding portfolio details in the database, so that the portfolio becomes device/browser agnostic, once       the user signsup and logs in. (Task breakdown coming up)
    - Sync with currency exchanges, to get near real time portfolio information.
      This would eliminate the manual entry of how much asset user is holding.
    - Charts showing price performance.
    - Interesting articles/tweets/telegram messages personalized for the user, depending on the crypto currencies in user's portfolio.
      
Real time data retrieved from public apis powered by https://www.CoinMarketCap.com
Data for currency exchange rates from http://fixer.io/

Help out a fellow developer :)
Ripple (XRP) : rEb8TK3gBgk5auZkwc6sHnwrGVJH8DuaLh 
Ripple (XRP) Tag : 105680413 
Ethereum (ETH) : 0xc02b304dc77b45fdad2f056ae9ceaf2fc0ddc57a

