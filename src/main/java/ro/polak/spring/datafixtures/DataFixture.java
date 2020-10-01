package ro.polak.spring.datafixtures;

/**
 * Core Data Fixture interface. User defined Data Fixtures must be beans that extend this
 * repository.
 */
public interface DataFixture {

  DataFixtureType getType();

  boolean shouldBeApplied();

  void apply();
}
