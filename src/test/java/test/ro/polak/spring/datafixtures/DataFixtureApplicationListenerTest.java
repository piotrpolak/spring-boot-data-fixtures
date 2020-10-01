package test.ro.polak.spring.datafixtures;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import ro.polak.spring.datafixtures.DataFixture;
import ro.polak.spring.datafixtures.DataFixturesAutoConfiguration;
import test.ro.polak.spring.datafixtures.samples.GenericCountableDataFixture;
import test.ro.polak.spring.datafixtures.samples.SampleDemoDisabledDataFixture;
import test.ro.polak.spring.datafixtures.samples.SampleDictionaryDataFixture;
import test.ro.polak.spring.datafixtures.samples.SamplePerformanceDataFixture;

import static org.assertj.core.api.Assertions.assertThat;
import static ro.polak.spring.datafixtures.DataFixtureType.DEMO;

@ActiveProfiles("dictionayandpeformance")
@Import(DataFixtureApplicationListenerTest.AdditionalFixturesConfiguration.class)
class DataFixtureApplicationListenerTest extends BaseTest {

  @Autowired SampleDictionaryDataFixture sampleDictionaryDataFixture;
  @Autowired SamplePerformanceDataFixture samplePerformanceDataFixture;
  @Autowired SampleDemoDisabledDataFixture sampleDemoDisabledDataFixture;

  @Autowired(required = false)
  DataFixturesAutoConfiguration dataFixturesAutoConfiguration;

  @Test
  void should_enable_autoconfiguration() {
    assertThat(dataFixturesAutoConfiguration).as("AutoConfiguration working").isNotNull();
  }

  @Test
  void should_load_fixtures_on_startup() {
    assertThat(sampleDictionaryDataFixture.getCallCount()).isEqualTo(1);
    assertThat(samplePerformanceDataFixture.getCallCount()).isEqualTo(1);
    assertThat(sampleDemoDisabledDataFixture.getCallCount()).isEqualTo(0);
  }

  @Configuration
  static class AdditionalFixturesConfiguration {

    @Bean
    DataFixture sampleDictionaryDataFixture() {
      return new SampleDictionaryDataFixture();
    }

    @Bean
    DataFixture samplePerformanceDataFixture() {
      return new SamplePerformanceDataFixture();
    }

    @Bean
    DataFixture sampleDemoDisabledDataFixture() {
      return new SampleDemoDisabledDataFixture();
    }
  }
}
