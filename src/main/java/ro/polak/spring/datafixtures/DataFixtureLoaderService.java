package ro.polak.spring.datafixtures;

import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

public class DataFixtureLoaderService {

  private static Logger LOG = LoggerFactory.getLogger(DataFixtureLoaderService.class);

  private final List<DataFixture> dataFixtures;

  public DataFixtureLoaderService(final List<DataFixture> dataFixtures) {
    this.dataFixtures = dataFixtures;
  }

  /** Applies all eligible fixtures for multiple types. */
  public long applyByTypesMatching(Set<DataFixtureType> types) {
    return types.stream().sorted().map(t -> applyBySingleType(t)).reduce(0L, Long::sum);
  }

  /** Applies all eligible fixtures by a single type. */
  public long applyBySingleType(final DataFixtureType type) {
    return dataFixtures.stream()
        .filter(f -> f.getType() == type && f.shouldBeApplied())
        .peek(
            fixture -> {
              StopWatch stopWatch = new StopWatch();
              stopWatch.start();
              fixture.apply();
              stopWatch.stop();
              LOG.info(
                  "Applied {} fixture of type {} in {} seconds",
                  fixture.getClass().getSimpleName(),
                  fixture.getType(),
                  String.format("%.3f", stopWatch.getTotalTimeSeconds()));
            })
        .count();
  }
}
