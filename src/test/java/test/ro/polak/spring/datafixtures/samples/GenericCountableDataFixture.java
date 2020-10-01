package test.ro.polak.spring.datafixtures.samples;

import ro.polak.spring.datafixtures.DataFixture;
import ro.polak.spring.datafixtures.DataFixtureType;

public class GenericCountableDataFixture implements DataFixture, ApplyCountable {

  private int callCount = 0;

  private final DataFixtureType type;
  private final boolean shouldBeApplied;

  public GenericCountableDataFixture(final DataFixtureType type) {
    this(type, true);
  }

  public GenericCountableDataFixture(final DataFixtureType type, final boolean shouldBeApplied) {
    this.type = type;
    this.shouldBeApplied = shouldBeApplied;
  }

  @Override
  public DataFixtureType getType() {
    return type;
  }

  @Override
  public boolean shouldBeApplied() {
    return shouldBeApplied;
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
