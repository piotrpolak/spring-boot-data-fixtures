package test.ro.polak.springboot.datafixtures.samples;

import static ro.polak.springboot.datafixtures.DataFixtureSet.DEMO;

public class SampleDemoDisabledDataFixture extends GenericCountableDataFixture {

  public SampleDemoDisabledDataFixture() {
    super(DEMO, false);
  }
}
