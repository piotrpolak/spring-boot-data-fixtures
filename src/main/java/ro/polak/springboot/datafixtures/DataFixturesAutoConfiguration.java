package ro.polak.springboot.datafixtures;

import static ro.polak.springboot.datafixtures.DataFixturesProperties.ENABLED;

import java.util.List;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(DataFixturesProperties.class)
public class DataFixturesAutoConfiguration {

  @Bean
  @ConditionalOnProperty(name = ENABLED, havingValue = "true", matchIfMissing = true)
  DataFixtureLoaderService dataFixtureLoaderService(final List<DataFixture> dataFixtures) {
    return new DataFixtureLoaderService(dataFixtures);
  }

  @Bean
  @ConditionalOnProperty(name = ENABLED, havingValue = "true", matchIfMissing = true)
  DataFixtureApplicationListener dataFixtureApplicationListener(
      final DataFixtureLoaderService dataFixtureLoaderService,
      final DataFixturesProperties dataFixturesProperties) {
    return new DataFixtureApplicationListener(dataFixtureLoaderService, dataFixturesProperties);
  }
}
