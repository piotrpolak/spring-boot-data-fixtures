package ro.polak.springboot.datafixtures;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = DataFixturesProperties.PREFIX)
@ConstructorBinding
@Validated
public class DataFixturesProperties {

  public static final String PREFIX = "data-fixtures";
  public static final String ENABLED = PREFIX + ".enabled";

  private final boolean enabled = true;

  private final Set<DataFixtureSet> sets = new HashSet<>(Arrays.asList(DataFixtureSet.DICTIONARY));

  public boolean isEnabled() {
    return enabled;
  }

  public Set<DataFixtureSet> getSets() {
    return sets;
  }
}
