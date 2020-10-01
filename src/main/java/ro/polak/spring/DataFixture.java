package ro.polak.spring;

public interface DataFixture {

  DataFixtureType getType();

  boolean shouldBeApplied();

  void apply();
}
