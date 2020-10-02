package ro.polak.spring.datafixtures;

/**
 * Core Data Fixture interface. User defined Data Fixtures must be beans that extend this interface.
 */
public interface DataFixture {

  /**
   * Defines the fixture type. Fixtures are loaded in the order defined by DataFixtureType enum
   * ordinals.
   */
  DataFixtureType getType();

  /**
   * Tells whether the fixture is eligible to be applied. In most cases a fixture is executed upon
   * the fist application startup only.
   */
  boolean shouldBeApplied();

  /**
   * The actual application of the fixture. Assuming that data fixtures are registered as beans,
   * this can contain a call to other services and/or repositories.
   */
  void apply();
}
