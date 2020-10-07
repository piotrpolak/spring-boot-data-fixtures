package test.ro.polak.springboot.datafixtures;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import ro.polak.springboot.datafixtures.DataFixturesAutoConfiguration;
import ro.polak.springboot.datafixtures.DataFixturesProperties;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("disabled")
class DataFixtureApplicationListenerDisabledTest extends BaseTest {

  @Autowired(required = false)
  private DataFixturesAutoConfiguration dataFixturesAutoConfiguration;

  @Autowired(required = false)
  private DataFixturesProperties dataFixturesProperties;

  @Test
  void should_not_enable_autoconfiguration() {
    assertThat(dataFixturesProperties).as("AutoConfiguration disabled").isNull();
    assertThat(dataFixturesAutoConfiguration).as("AutoConfiguration disabled").isNull();
  }
}
