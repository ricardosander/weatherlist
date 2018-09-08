package br.com.ricardosander.weatherlist.config;

import br.com.ricardosander.weatherlist.services.RecommendationService;
import br.com.ricardosander.weatherlist.services.RecommendationServiceImplementation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RecommendationConfiguration {

  @Bean
  public RecommendationService recommendationService() {
    return new RecommendationServiceImplementation();
  }

}
