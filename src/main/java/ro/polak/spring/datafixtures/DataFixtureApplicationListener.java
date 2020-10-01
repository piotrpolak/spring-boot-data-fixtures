package ro.polak.spring.datafixtures;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

public class DataFixtureApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

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
    dataFixturesProperties.getTypes().stream()
        .distinct()
        .sorted()
        .forEach(t -> dataFixtureLoaderService.applyByType(t));
  }
}
