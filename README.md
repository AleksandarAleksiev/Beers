# Beers
The app loads and display list of beers from Punk API. When the user taps on a beer from the list a new screen is opened where the user can see details of the selected beer.

The app is written in Kotlin and uses Kotlin coroutines for asynchronous calls, Hilt for DI, Ktor for http client, kotlinx serialization for JSON parser and Android Architecture Components.

If I had more time I would
  
    o	implement pagination for the list of beers
    o	implement persistent caching
    o	add integration UI test
