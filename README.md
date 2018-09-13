# Dependecy
* Docker 18.06
* Maven 3.3.9 (for building)
* Java JDK 1.8 (for building)

# Running

You can run the application by running the following command line.

```
docker run -d -p 8080:8080 ricardosander/weatherlist
```

# Deploying
If you want to build it on your own machine you can run the following command

```
docker build -t weatherlist .
```
and then create a container by running

```
docker run -d -p 8080:8080 weatherlist
```

# API documentation
[API documentation on swagger](https://app.swaggerhub.com/apis/ricardosander/weatherlist/1.0.0)

# iFood Backend Advanced Test

Create a micro-service able to accept RESTful requests receiving as parameter either city name or lat long coordinates and returns a playlist (only track names is fine) suggestion according to the current temperature.

## Business rules

* If temperature (celcius) is above 30 degrees, suggest tracks for party
* In case temperature is between 15 and 30 degrees, suggest pop music tracks
* If it's a bit chilly (between 10 and 14 degrees), suggest rock music tracks
* Otherwise, if it's freezing outside, suggests classical music tracks 

## Hints

You can make usage of OpenWeatherMaps API (https://openweathermap.org) to fetch temperature data and Spotify (https://developer.spotify.com) to suggest the tracks as part of the playlist.

## Non functional requirements

As this service will be a worldwide success, it must be prepared to be fault tolerant, responsive and resilient.

Use whatever language, tools and frameworks you feel comfortable to, and briefly elaborate on your solution, architecture details, choice of patterns and frameworks.

Also, make it easy to deploy/run your service(s) locally (consider using some container/vm solution for this). Once done, share your code with us.
