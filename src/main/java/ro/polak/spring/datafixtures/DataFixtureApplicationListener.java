package ro.polak.spring.datafixtures;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

@ConditionalOnBean(DataFixturesAutoConfiguration.class)
class DataFixtureApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

  private static Logger LOG = LoggerFactory.getLogger(DataFixtureApplicationListener.class);

  private final DataFixtureLoaderService dataFixtureLoaderService;
  private final DataFixturesProperties dataFixturesProperties;

  public DataFixtureApplicationListener(
      final DataFixtureLoaderService dataFixtureLoaderService,
      final DataFixturesProperties dataFixturesProperties) {
    this.dataFixtureLoaderService = dataFixtureLoaderService;
    this.dataFixturesProperties = dataFixturesProperties;
  }

  @Override
  public void onApplicationEvent(final ContextRefreshedEvent contextStartedEvent) {

    if (dataFixturesProperties.getSets().isEmpty()) {
      LOG.info("Data Fixtures feature is enabled but there are no fixture sets configured.");
      return;
    }

    dataFixtureLoaderService.applyByTypesMatching(dataFixturesProperties.getSets());
  }
}
