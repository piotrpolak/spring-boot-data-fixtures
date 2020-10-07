package test.ro.polak.spring.datafixtures;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import ro.polak.spring.datafixtures.DataFixturesAutoConfiguration;
import ro.polak.spring.datafixtures.DataFixturesProperties;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("enabledbutnosets")
class DataFixtureApplicationListenerEnabledButNoSetsTest extends BaseTest {

  @Autowired private DataFixturesAutoConfiguration dataFixturesAutoConfiguration;

  @Autowired private DataFixturesProperties dataFixturesProperties;

  @Test
  void should_not_enable_autoconfiguration() {
    assertThat(dataFixturesAutoConfiguration).as("AutoConfiguration working").isNotNull();
    assertThat(dataFixturesProperties).as("AutoConfiguration working - properties").isNotNull();
    assertThat(dataFixturesProperties.isEnabled()).isTrue();
    assertThat(dataFixturesProperties.getSets()).isEmpty();
  }
}
