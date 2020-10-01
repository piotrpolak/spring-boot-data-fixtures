package test.ro.polak.spring.datafixtures;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import ro.polak.spring.datafixtures.DataFixture;
import ro.polak.spring.datafixtures.DataFixturesAutoConfiguration;
import test.ro.polak.spring.datafixtures.samples.SampleDictionaryDataFixture;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("dictionaryonly")
@Import(DataFixtureApplicationListenerTest.AdditionalFixturesConfiguration.class)
class DataFixtureApplicationListenerTest extends BaseTest {

  @Autowired SampleDictionaryDataFixture sampleDictionaryDataFixture;

  @Autowired(required = false)
  DataFixturesAutoConfiguration dataFixturesAutoConfiguration;

  @Test
  void should_enable_autoconfiguration() {
    assertThat(dataFixturesAutoConfiguration).as("AutoConfiguration working").isNotNull();
  }

  @Test
  void should_load_fixtures_on_startup() {
    assertThat(sampleDictionaryDataFixture.getCallCount()).isEqualTo(1);
  }

  @Configuration
  static class AdditionalFixturesConfiguration {

    @Bean
    DataFixture sampleDictionaryDataFixture() {
      return new SampleDictionaryDataFixture();
    }
  }
}
