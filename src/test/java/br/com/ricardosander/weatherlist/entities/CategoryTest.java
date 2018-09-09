package br.com.ricardosander.weatherlist.entities;

import static br.com.ricardosander.weatherlist.entities.Category.CLASSIC;
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
    Category category = Category.getInstance(new Weather(VERY_HOT_IN_CELCIUS));
    assertEquals(PARTY, category);
  }

  @Test
  public void testAboveHotTemperature() {
    Category category = Category.getInstance(new Weather(PARTY_TEMPERATURE_IN_CELCIUS + 1));
    assertEquals(PARTY, category);
  }

  @Test
  public void testHotTemperature() {
    Category category = Category.getInstance(new Weather(PARTY_TEMPERATURE_IN_CELCIUS));
    assertEquals(POP, category);
  }

  @Test
  public void testBellowHotTemperature() {
    Category category = Category.getInstance(new Weather(PARTY_TEMPERATURE_IN_CELCIUS - 1));
    assertEquals(POP, category);
  }

  @Test
  public void testAboveRegularTemperature() {
    Category category = Category.getInstance(new Weather(POP_TEMPERATURE_IN_CELCIUS + 1));
    assertEquals(POP, category);
  }

  @Test
  public void testRegularTemperature() {
    Category category = Category.getInstance(new Weather(REGULAR_IN_CELCIUS + 1));
    assertEquals(POP, category);
  }

  @Test
  public void testBelowRegularTemperature() {
    Category category = Category.getInstance(new Weather(POP_TEMPERATURE_IN_CELCIUS - 1));
    assertEquals(ROCK, category);
  }

  @Test
  public void testAboveChillyTemperature() {
    Category category = Category.getInstance(new Weather(ROCK_TEMPERATURE_IN_CELCIUS + 1));
    assertEquals(ROCK, category);
  }

  @Test
  public void testChillyTemperature() {
    Category category = Category.getInstance(new Weather(CHILLY_IN_CELCIUS));
    assertEquals(ROCK, category);
  }

  @Test
  public void testBelowChillyTemperature() {
    Category category = Category.getInstance(new Weather(ROCK_TEMPERATURE_IN_CELCIUS - 1));
    assertEquals(CLASSIC, category);
  }

  @Test
  public void testAboveFreezingChillyTemperature() {
    Category category = Category.getInstance(new Weather(FREEZING_IN_CELCIUS + 1));
    assertEquals(CLASSIC, category);
  }

  @Test
  public void testFreezingChillyTemperature() {
    Category category = Category.getInstance(new Weather(FREEZING_IN_CELCIUS));
    assertEquals(CLASSIC, category);
  }

  @Test
  public void testBelowFreezingChillyTemperature() {
    Category category = Category.getInstance(new Weather(FREEZING_IN_CELCIUS - 1));
    assertEquals(CLASSIC, category);
  }

}