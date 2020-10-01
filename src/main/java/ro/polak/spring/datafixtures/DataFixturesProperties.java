package ro.polak.spring.datafixtures;

import static ro.polak.spring.datafixtures.DataFixtureType.DICTIONARY;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "ro.polak.spring.data-fixtures")
@ConstructorBinding
@Validated
public class DataFixturesProperties {

  private final boolean enabled = true;

  private final Set<DataFixtureType> types = new HashSet<>(Arrays.asList(DICTIONARY));

  public boolean isEnabled() {
    return enabled;
  }

  public Set<DataFixtureType> getTypes() {
    return types;
  }
}
