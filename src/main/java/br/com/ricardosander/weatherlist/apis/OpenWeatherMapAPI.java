package br.com.ricardosander.weatherlist.apis;

import br.com.ricardosander.weatherlist.apis.openweatherapi.OpenWeatherMapConfiguration;
import br.com.ricardosander.weatherlist.dto.GeographicCoordinate;
import br.com.ricardosander.weatherlist.entities.Weather;
import br.com.ricardosander.weatherlist.services.exceptions.ObjectNotFoundException;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import net.aksingh.owmjapis.core.OWM;
import net.aksingh.owmjapis.model.CurrentWeather;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class OpenWeatherMapAPI implements WeatherAPI {

  private final OWM openWeatherMapApi;

  private final Cache<String, Weather> cityWeatherCache;

  private final Cache<GeographicCoordinate, Weather> geoCoordinateWeatherCache;

  public OpenWeatherMapAPI(OpenWeatherMapConfiguration weatherApiConfiguration) {

    openWeatherMapApi = new OWM(weatherApiConfiguration.getKey());

    cityWeatherCache = CacheBuilder
        .newBuilder()
        .expireAfterAccess(1, TimeUnit.MINUTES)
        .build();

    geoCoordinateWeatherCache = CacheBuilder
        .newBuilder()
        .expireAfterAccess(1, TimeUnit.MINUTES)
        .build();

  }

  @Override
  public Weather findWeatherByCityName(String cityName) {

    try {

      return cityWeatherCache.get(cityName, () -> {

        CurrentWeather currentWeather = openWeatherMapApi.currentWeatherByCityName(cityName);

        if (currentWeather.getMainData() == null
            || currentWeather.getMainData().getTemp() == null) {
          throw new ObjectNotFoundException("City with city name " + cityName + " not found.");
        }

        final Weather weather = new Weather(currentWeather.getMainData().getTemp() - 273);
        if (currentWeather.getCoordData() != null
            && currentWeather.getCoordData().getLatitude() != null
            && currentWeather.getCoordData().getLongitude() != null) {

          final GeographicCoordinate geoCoordinate =
              GeographicCoordinate.newInstance(currentWeather.getCoordData().getLatitude(),
                  currentWeather.getCoordData().getLongitude());

          return geoCoordinateWeatherCache.get(geoCoordinate, () -> weather);
        }

        return weather;
      });

    } catch (ExecutionException e) {
      e.printStackTrace();//TODO handle exception
    }

    throw new ObjectNotFoundException("City with city name " + cityName + " not found.");
  }

  @Override
  public Weather findWeather(GeographicCoordinate geographicCoordinate) {

    try {

      return geoCoordinateWeatherCache.get(geographicCoordinate, () -> {

        final CurrentWeather currentWeather =
            openWeatherMapApi.currentWeatherByCoords(geographicCoordinate.getLatitude(),
                geographicCoordinate.getLongitude());

        if (currentWeather.getMainData() == null
            || currentWeather.getMainData().getTemp() == null) {
          throw new ObjectNotFoundException(geographicCoordinate.toString() + " not found.");
        }

        final String cityName = currentWeather.getCityName();

        final Weather weather = new Weather(currentWeather.getMainData().getTemp() - 273);

        if (cityName != null && !cityName.isEmpty()) {
          return cityWeatherCache.get(cityName, () -> weather);
        }

        return weather;
      });

    } catch (ExecutionException e) {
      e.printStackTrace();//TODO handle exception
    }

    throw new ObjectNotFoundException(geographicCoordinate.toString() + " not found.");
  }

}
