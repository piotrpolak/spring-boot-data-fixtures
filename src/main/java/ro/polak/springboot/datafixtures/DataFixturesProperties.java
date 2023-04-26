package ro.polak.springboot.datafixtures;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.springframework.boot.context.properties.ConfigurationProperties;

/** Defines configuration properties. */
@ConfigurationProperties(prefix = DataFixturesProperties.PREFIX)
public class DataFixturesProperties {

  public static final String PREFIX = "data-fixtures";
  public static final String ENABLED = PREFIX + ".enabled";

  /** Enables or disables data fixtures loading upon application startup. */
  private boolean enabled = true;

  /** The collection of sets to be loaded upon application startup. */
  private Set<DataFixtureSet> sets = new HashSet<>(Arrays.asList(DataFixtureSet.DICTIONARY));

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public Set<DataFixtureSet> getSets() {
    return sets;
  }

  public void setSets(Set<DataFixtureSet> sets) {
    this.sets = sets;
  }
}
