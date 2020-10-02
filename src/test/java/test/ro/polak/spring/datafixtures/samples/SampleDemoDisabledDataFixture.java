package test.ro.polak.spring.datafixtures.samples;

import static ro.polak.spring.datafixtures.DataFixtureSet.DEMO;

public class SampleDemoDisabledDataFixture extends GenericCountableDataFixture {

  public SampleDemoDisabledDataFixture() {
    super(DEMO, false);
  }
}
