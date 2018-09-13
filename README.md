# Weatherlist API

This application service give you a playlist based on your location.

## API documentation
[API documentation on swagger](https://app.swaggerhub.com/apis/ricardosander/weatherlist/1.0.0)

## Dependecy
* Docker 18.06
* Maven 3.3.9 (for building)
* Java JDK 1.8 (for building)

## Running

You can run the application by running the following command line.

```
docker run -d -p 8080:8080 ricardosander/weatherlist
```

## Deploying

If you want to build it on your own machine, first you need to configure the API keys. See
[configuration section](#configuration)
After proper configuring you can run the following command

```
docker build -t weatherlist .
```
and then create a container by running

```
docker run -d -p 8080:8080 weatherlist
```

## Configuration

You will find the application configuration on the file 
[application.properties](src/main/resources/application.properties).  
Some configuration have default values but others (API keys) needs to bem set up when building the
application.

- openWeatherMap.key : key for using [OpenWeatherMap API](https://openweathermap.org/api)
- openWeatherMap.cityCacheTimeInMinutes : time, in minutes, for caching weather by city. Default: 15
- openWeatherMap.geoCoordinatesCacheTimeInMinutes : time, in minutes, for caching weather by geo
coordinates. Default: 15
- openWeatherMap.cityCacheSize : cache size for weather by city. Default: 100
- openWeatherMap.geoCoordinatesCacheTimeInMinutes : cache size for weather by geo coordinates.
Default: 100
- spotify.clientId : client id for using [Spotify API](https://developer.spotify.com)
- spotify.secret : secret for using for using [Spotify API](https://developer.spotify.com)
- spotify.limitPlaylistTracks : Number of tracks to be retrieved by playlist. Default: 30
- spotify.playListCacheInfoTimeInMinutes : time, in minutes, for caching info from playlist.
Default: 720
- spotify.playListCacheTimeInMinutes : time, in minutes, for caching playlist. Default : 60
- defaultTracks : the list of tracks for the default playlist used when having problems retrieving
information from the APIs.

## Project Details
 - Language: Java, because I love it. And because Spring Boot;
 - Spring Boot : because it is reliable and simple and easy to develop, deploy and run. "Make JAR,
 not WAR";
 - Spring : the application uses dependency injection and bean configuration and annotations to
 identify controllers, exception handlers, services and beans;
 - Dependencies : Maven, because it is reliable and I know better;
 - jUnit for unit test;
 - Mockito for mocking objects on testing and development;
 - [OpenWeatherMap Library](https://bitbucket.org/aksinghnet/owm-japis)
 - [Spotify Library](https://github.com/thelinmichael/spotify-web-api-java)
 - Guava Cache : because is very simple to make customize logic and I know better;
 
### Caching
The application uses cache to reduce the needed of calling external APIs. For playlist is not needed
to limit the cache size because we will have a limit number of playlist (based on categories).
The application catch the opportunity of weather API returning temperature, city name and
geographic coordinates for each call and already add cache for both cases (city name and
geo coordinates).

### Geographic Coordinates
Based on some research and testing, I found out the second decimal base from latitude and longitude
is enough to guarantee the same city. Base on the fact that the temperature variation on a city isn't
so significant, I decided to round any incoming latitude and longitude to 2 decimal places for better
caching. 