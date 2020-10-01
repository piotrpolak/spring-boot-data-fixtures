package ro.polak.spring;

import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "ro.polak.spring.data-fixtures")
@ConstructorBinding
@Validated
public class DataFixturesProperties {

  private final boolean enabled = true;

  private final List<DataFixtureType> types = new ArrayList();

  public boolean isEnabled() {
    return enabled;
  }

  public List<DataFixtureType> getTypes() {
    return types;
  }
}
