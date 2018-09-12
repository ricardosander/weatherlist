package br.com.ricardosander.weatherlist.entities;

import static br.com.ricardosander.weatherlist.entities.Category.CLASSICAL;
import static br.com.ricardosander.weatherlist.entities.Category.PARTY;
import static br.com.ricardosander.weatherlist.entities.Category.PARTY_TEMPERATURE_IN_CELCIUS;
import static br.com.ricardosander.weatherlist.entities.Category.POP;
import static br.com.ricardosander.weatherlist.entities.Category.POP_TEMPERATURE_IN_CELCIUS;
import static br.com.ricardosander.weatherlist.entities.Category.ROCK;
import static br.com.ricardosander.weatherlist.entities.Category.ROCK_TEMPERATURE_IN_CELCIUS;
import static org.junit.Assert.*;

import org.junit.Test;

public class CategoryTest {

  private static final int VERY_HOT_IN_CELCIUS = 45;
  private static final int REGULAR_IN_CELCIUS = 20;
  private static final int CHILLY_IN_CELCIUS = 12;
  private static final int FREEZING_IN_CELCIUS = 5;

  @Test
  public void testVeryHotTemperature() {
    Category category = Category.getInstance(Weather.fromCelcius(VERY_HOT_IN_CELCIUS));
    assertEquals(PARTY, category);
  }

  @Test
  public void testAboveHotTemperature() {
    Category category = Category.getInstance(Weather.fromCelcius(PARTY_TEMPERATURE_IN_CELCIUS + 1));
    assertEquals(PARTY, category);
  }

  @Test
  public void testHotTemperature() {
    Category category = Category.getInstance(Weather.fromCelcius(PARTY_TEMPERATURE_IN_CELCIUS));
    assertEquals(POP, category);
  }

  @Test
  public void testBellowHotTemperature() {
    Category category = Category.getInstance(Weather.fromCelcius(PARTY_TEMPERATURE_IN_CELCIUS - 1));
    assertEquals(POP, category);
  }

  @Test
  public void testAboveRegularTemperature() {
    Category category = Category.getInstance(Weather.fromCelcius(POP_TEMPERATURE_IN_CELCIUS + 1));
    assertEquals(POP, category);
  }

  @Test
  public void testRegularTemperature() {
    Category category = Category.getInstance(Weather.fromCelcius(REGULAR_IN_CELCIUS + 1));
    assertEquals(POP, category);
  }

  @Test
  public void testBelowRegularTemperature() {
    Category category = Category.getInstance(Weather.fromCelcius(POP_TEMPERATURE_IN_CELCIUS - 1));
    assertEquals(ROCK, category);
  }

  @Test
  public void testAboveChillyTemperature() {
    Category category = Category.getInstance(Weather.fromCelcius(ROCK_TEMPERATURE_IN_CELCIUS + 1));
    assertEquals(ROCK, category);
  }

  @Test
  public void testChillyTemperature() {
    Category category = Category.getInstance(Weather.fromCelcius(CHILLY_IN_CELCIUS));
    assertEquals(ROCK, category);
  }

  @Test
  public void testBelowChillyTemperature() {
    Category category = Category.getInstance(Weather.fromCelcius(ROCK_TEMPERATURE_IN_CELCIUS - 1));
    assertEquals(CLASSICAL, category);
  }

  @Test
  public void testAboveFreezingChillyTemperature() {
    Category category = Category.getInstance(Weather.fromCelcius(FREEZING_IN_CELCIUS + 1));
    assertEquals(CLASSICAL, category);
  }

  @Test
  public void testFreezingChillyTemperature() {
    Category category = Category.getInstance(Weather.fromCelcius(FREEZING_IN_CELCIUS));
    assertEquals(CLASSICAL, category);
  }

  @Test
  public void testBelowFreezingChillyTemperature() {
    Category category = Category.getInstance(Weather.fromCelcius(FREEZING_IN_CELCIUS - 1));
    assertEquals(CLASSICAL, category);
  }

}