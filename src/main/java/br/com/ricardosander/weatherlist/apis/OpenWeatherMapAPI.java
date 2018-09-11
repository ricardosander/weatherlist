package br.com.ricardosander.weatherlist.apis;

import br.com.ricardosander.weatherlist.apis.openweatherapi.OpenWeatherMapConfiguration;
import br.com.ricardosander.weatherlist.dto.GeographicCoordinate;
import br.com.ricardosander.weatherlist.entities.Weather;
import br.com.ricardosander.weatherlist.services.exceptions.ObjectNotFoundException;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import net.aksingh.owmjapis.api.APIException;
import net.aksingh.owmjapis.core.OWM;
import net.aksingh.owmjapis.model.CurrentWeather;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class OpenWeatherMapAPI implements WeatherAPI {

  private final OWM openWeatherMapApi;

  private final Cache<String, Weather> cityWeatherCache;

  public OpenWeatherMapAPI(OpenWeatherMapConfiguration weatherApiConfiguration) {

    openWeatherMapApi = new OWM(weatherApiConfiguration.getKey());

    cityWeatherCache = CacheBuilder
        .newBuilder()
        .expireAfterAccess(1, TimeUnit.MINUTES)
        .build();

  }

  @Override
  public Weather findWeatherByCityName(String cityName) {

    try {

      return cityWeatherCache.get(cityName, () -> {

        CurrentWeather currentWeather = openWeatherMapApi.currentWeatherByCityName(cityName);

        return new Weather(currentWeather.getMainData().getTemp() - 273);
      });

    } catch (ExecutionException e) {
      e.printStackTrace();
    }

    throw new ObjectNotFoundException("City with city name " + cityName + " not found.");
  }

  @Override
  public Weather findWeather(GeographicCoordinate geographicCoordinate) {

    try {

      final CurrentWeather currentWeather =
          openWeatherMapApi.currentWeatherByCoords(geographicCoordinate.getLatitude(),
              geographicCoordinate.getLongitude());

      return new Weather(currentWeather.getMainData().getTemp() - 273);

    } catch (APIException e) {
      e.printStackTrace();
    }

    throw new ObjectNotFoundException(geographicCoordinate.toString() + " not found.");
  }

}
