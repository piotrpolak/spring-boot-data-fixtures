package ro.polak.springboot.datafixtures;

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

  /** Applies all eligible fixtures for multiple sets. */
  public long applyByTypesMatching(Set<DataFixtureSet> sets) {
    return sets.stream().sorted().map(t -> applyBySingleType(t)).reduce(0L, Long::sum);
  }

  /** Applies all eligible fixtures by a single set. */
  public long applyBySingleType(final DataFixtureSet set) {
    return dataFixtures.stream()
        .filter(f -> f.getSet() == set && f.shouldBeApplied())
        .peek(
            fixture -> {
              StopWatch stopWatch = new StopWatch();
              stopWatch.start();
              fixture.apply();
              stopWatch.stop();
              LOG.info(
                  "Applied {} fixture of set {} in {} seconds",
                  fixture.getClass().getSimpleName(),
                  fixture.getSet(),
                  String.format("%.3f", stopWatch.getTotalTimeSeconds()));
            })
        .count();
  }
}
