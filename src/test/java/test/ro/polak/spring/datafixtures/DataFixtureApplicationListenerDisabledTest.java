package test.ro.polak.spring.datafixtures;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import ro.polak.spring.datafixtures.DataFixture;
import ro.polak.spring.datafixtures.DataFixturesAutoConfiguration;
import ro.polak.spring.datafixtures.DataFixturesProperties;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("disabled")
class DataFixtureApplicationListenerDisabledTest extends BaseTest {

  @Autowired(required = false)
  DataFixturesAutoConfiguration dataFixturesAutoConfiguration;

  @Autowired(required = false)
  DataFixturesProperties dataFixturesProperties;

  @Test
  void should_not_enable_autoconfiguration() {
    assertThat(dataFixturesProperties).as("AutoConfiguration disabled").isNull();
    assertThat(dataFixturesAutoConfiguration).as("AutoConfiguration disabled").isNull();
  }
}
