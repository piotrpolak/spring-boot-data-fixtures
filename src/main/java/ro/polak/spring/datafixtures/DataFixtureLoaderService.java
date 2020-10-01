package ro.polak.spring.datafixtures;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataFixtureLoaderService {

  private static Logger LOG = LoggerFactory.getLogger(DataFixtureLoaderService.class);

  private final List<DataFixture> dataFixtures;

  public DataFixtureLoaderService(final List<DataFixture> dataFixtures) {
    this.dataFixtures = dataFixtures;
  }

  /** Resolves and loads fixture by type. */
  public long applyByType(final DataFixtureType type) {
    return dataFixtures.stream()
        .filter(f -> f.getType() == type && f.shouldBeApplied())
        .peek(
            fixture -> {
              fixture.apply();
              LOG.info(
                  "Applied {} fixture of type {}",
                  fixture.getClass().getSimpleName(),
                  fixture.getType());
            })
        .count();
  }
}
