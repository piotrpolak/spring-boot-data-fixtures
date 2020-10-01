package test.ro.polak.spring.datafixtures;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import ro.polak.spring.datafixtures.DataFixturesAutoConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("disabled")
class DataFixtureApplicationListenerDisabledTest extends BaseTest {

  @Autowired(required = false)
  DataFixturesAutoConfiguration dataFixturesAutoConfiguration;

  @Test
  void should_not_enable_autoconfiguration() {
    assertThat(dataFixturesAutoConfiguration).as("AutoConfiguration disabled").isNull();
  }
}
