package br.com.ricardosander.weatherlist.apis.openweatherapi;

import br.com.ricardosander.weatherlist.apis.WeatherAPI;
import br.com.ricardosander.weatherlist.apis.exceptions.ApiUnavailableException;
import br.com.ricardosander.weatherlist.dto.GeographicCoordinate;
import br.com.ricardosander.weatherlist.entities.Weather;
import br.com.ricardosander.weatherlist.services.exceptions.ObjectNotFoundException;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import net.aksingh.owmjapis.core.OWM;
import net.aksingh.owmjapis.model.CurrentWeather;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class OpenWeatherMapAPI implements WeatherAPI {

  private final Logger logger = LoggerFactory.getLogger(OpenWeatherMapAPI.class);

  private final OWM openWeatherMapApi;

  private final Cache<String, Weather> cityWeatherCache;

  private final Cache<GeographicCoordinate, Weather> geoCoordinateWeatherCache;

  public OpenWeatherMapAPI(OpenWeatherMapConfiguration weatherApiConfiguration) {

    openWeatherMapApi = new OWM(weatherApiConfiguration.getKey());

    cityWeatherCache = CacheBuilder
        .newBuilder()
        .expireAfterAccess(weatherApiConfiguration.getCityCacheTimeInMinutes(), TimeUnit.MINUTES)
        .maximumSize(weatherApiConfiguration.getCityCacheSize())
        .build();

    geoCoordinateWeatherCache = CacheBuilder
        .newBuilder()
        .expireAfterAccess(weatherApiConfiguration.getGeoCoordinatesCacheTimeInMinutes(),
            TimeUnit.MINUTES)
        .maximumSize(weatherApiConfiguration.getGeoCoordinatesCacheSize())
        .build();

  }

  @Override
  public Weather findWeatherByCityName(String cityName) throws ApiUnavailableException {

    try {

      return cityWeatherCache.get(cityName, () -> {

        logger.info("Calling OpenWeatherMap API by city name " + cityName);

        CurrentWeather currentWeather = openWeatherMapApi.currentWeatherByCityName(cityName);

        if (currentWeather.getMainData() == null
            || currentWeather.getMainData().getTemp() == null) {
          logger.info("City name " + cityName + " not found by OpenWeatherMap API.");
          throw new ObjectNotFoundException("City with city name " + cityName + " not found.");
        }

        final Weather weather = new Weather(currentWeather.getMainData().getTemp() - 273);
        if (currentWeather.getCoordData() != null
            && currentWeather.getCoordData().getLatitude() != null
            && currentWeather.getCoordData().getLongitude() != null) {

          final GeographicCoordinate geoCoordinate =
              GeographicCoordinate.newInstance(currentWeather.getCoordData().getLatitude(),
                  currentWeather.getCoordData().getLongitude());

          logger.info("Caching weather for city " + cityName + " and " + geoCoordinate.toString());
          return geoCoordinateWeatherCache.get(geoCoordinate, () -> weather);
        }

        logger.info("Caching Weather for city " + cityName);
        return weather;
      });

    } catch (ExecutionException e) {
      logger.error(e.getMessage());
      throw new OpenWeatherMapApiUnavailableException(e.getMessage());
    }

  }

  @Override
  public Weather findWeather(GeographicCoordinate geographicCoordinate)
      throws ApiUnavailableException {

    try {

      return geoCoordinateWeatherCache.get(geographicCoordinate, () -> {

        logger.info("Calling OpenWeatherMap API by " + geographicCoordinate.toString());

        final CurrentWeather currentWeather =
            openWeatherMapApi.currentWeatherByCoords(geographicCoordinate.getLatitude(),
                geographicCoordinate.getLongitude());

        if (currentWeather.getMainData() == null
            || currentWeather.getMainData().getTemp() == null) {
          logger.info(geographicCoordinate.toString() + " not found by OpenWeatherMap API.");
          throw new ObjectNotFoundException(geographicCoordinate.toString() + " not found.");
        }

        final String cityName = currentWeather.getCityName();

        final Weather weather = new Weather(currentWeather.getMainData().getTemp() - 273);

        if (cityName != null && !cityName.isEmpty()) {
          logger.info(
              "Caching weather for city " + cityName + " and " + geographicCoordinate.toString());
          return cityWeatherCache.get(cityName, () -> weather);
        }

        logger.info("Caching weather for " + geographicCoordinate.toString());
        return weather;
      });

    } catch (ExecutionException e) {
      logger.error(e.getMessage());
      throw new OpenWeatherMapApiUnavailableException(e.getMessage());
    }

  }

}
