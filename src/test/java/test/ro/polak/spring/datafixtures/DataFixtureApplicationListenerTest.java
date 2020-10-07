package test.ro.polak.spring.datafixtures;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import ro.polak.spring.datafixtures.DataFixture;
import ro.polak.spring.datafixtures.DataFixturesAutoConfiguration;
import ro.polak.spring.datafixtures.DataFixturesProperties;
import test.ro.polak.spring.datafixtures.samples.SampleDemoDisabledDataFixture;
import test.ro.polak.spring.datafixtures.samples.SampleDictionaryDataFixture;
import test.ro.polak.spring.datafixtures.samples.SamplePerformanceDataFixture;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("dictionayandpeformance")
@Import(DataFixtureApplicationListenerTest.AdditionalFixturesConfiguration.class)
class DataFixtureApplicationListenerTest extends BaseTest {

  @Autowired private SampleDictionaryDataFixture sampleDictionaryDataFixture;
  @Autowired private SamplePerformanceDataFixture samplePerformanceDataFixture;
  @Autowired private SampleDemoDisabledDataFixture sampleDemoDisabledDataFixture;
  @Autowired private DataFixturesProperties dataFixturesProperties;

  @Autowired(required = false)
  private DataFixturesAutoConfiguration dataFixturesAutoConfiguration;

  @Test
  void should_enable_autoconfiguration() {
    assertThat(dataFixturesAutoConfiguration).as("AutoConfiguration working").isNotNull();
    assertThat(dataFixturesProperties).as("AutoConfiguration working - properties").isNotNull();
    assertThat(dataFixturesProperties.isEnabled()).isTrue();
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
