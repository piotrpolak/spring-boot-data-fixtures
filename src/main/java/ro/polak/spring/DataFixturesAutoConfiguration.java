package ro.polak.spring;

import java.util.List;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@EnableConfigurationProperties(DataFixturesProperties.class)
public class DataFixturesAutoConfiguration {

  @Bean
  DataFixtureLoaderService dataFixtureLoaderService(final List<DataFixture> dataFixtures) {
    return new DataFixtureLoaderService(dataFixtures);
  }

  @Bean
  DataFixtureApplicationListener dataFixtureApplicationListener(
      final DataFixtureLoaderService dataFixtureLoaderService,
      final DataFixturesProperties dataFixturesProperties) {
    return new DataFixtureApplicationListener(dataFixtureLoaderService, dataFixturesProperties);
  }
}
