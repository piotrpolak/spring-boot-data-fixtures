package test.ro.polak.springboot.datafixtures.samples;

import ro.polak.springboot.datafixtures.DataFixture;
import ro.polak.springboot.datafixtures.DataFixtureSet;

public class GenericCountableDataFixture implements DataFixture, CallCountable {

  private int callCount = 0;

  private final DataFixtureSet set;
  private final boolean canBeLoaded;

  public GenericCountableDataFixture(final DataFixtureSet set) {
    this(set, true);
  }

  public GenericCountableDataFixture(final DataFixtureSet set, final boolean canBeLoaded) {
    this.set = set;
    this.canBeLoaded = canBeLoaded;
  }

  @Override
  public DataFixtureSet getSet() {
    return set;
  }

  @Override
  public boolean canBeLoaded() {
    return canBeLoaded;
  }

  @Override
  public void apply() {
    callCount++;
  }

  @Override
  public int getCallCount() {
    return callCount;
  }
}
