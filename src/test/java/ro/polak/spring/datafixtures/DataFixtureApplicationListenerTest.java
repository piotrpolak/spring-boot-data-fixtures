package ro.polak.spring.datafixtures;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
    properties = {
      "ro.polak.spring.data-fixtures.enabled=true",
      "ro.polak.spring.data-fixtures.types=DICTIONARY"
    })
@Import(DataFixturesAutoConfiguration.class)
class DataFixtureApplicationListenerTest {

  @Autowired SampleDictionaryDataFixture sampleDictionaryDataFixture;

  @Autowired(required = false)
  DataFixturesAutoConfiguration dataFixturesAutoConfiguration;

  @Test
  void should_load_fixtures_on_startup() {

    assertThat(dataFixturesAutoConfiguration).as("AutoConfiguration working").isNotNull();

    assertThat(sampleDictionaryDataFixture.wasCalled()).isTrue();
  }

  @Configuration
  static class AdditionalFixturesConfiguration {

    @Bean
    DataFixture sampleDictionaryDataFixture() {
      return new SampleDictionaryDataFixture();
    }
  }

  static class SampleDictionaryDataFixture implements DataFixture {

    private boolean wasCalled = false;

    @Override
    public DataFixtureType getType() {
      return DataFixtureType.DICTIONARY;
    }

    @Override
    public boolean shouldBeApplied() {
      return true;
    }

    @Override
    public void apply() {
      wasCalled = true;
    }

    public boolean wasCalled() {
      return wasCalled;
    }
  }
}
