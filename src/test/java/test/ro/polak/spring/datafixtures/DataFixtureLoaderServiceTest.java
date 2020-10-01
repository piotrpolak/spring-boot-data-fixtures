package test.ro.polak.spring.datafixtures;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Test;
import ro.polak.spring.datafixtures.DataFixture;
import ro.polak.spring.datafixtures.DataFixtureLoaderService;
import ro.polak.spring.datafixtures.DataFixtureType;
import test.ro.polak.spring.datafixtures.samples.GenericCountableDataFixture;
import test.ro.polak.spring.datafixtures.samples.OrderableGenericCountableDataFixture;

import static org.assertj.core.api.Assertions.assertThat;
import static ro.polak.spring.datafixtures.DataFixtureType.DEMO;
import static ro.polak.spring.datafixtures.DataFixtureType.DICTIONARY;
import static ro.polak.spring.datafixtures.DataFixtureType.PERFORMANCE;
import static ro.polak.spring.datafixtures.DataFixtureType.TEST;
import static ro.polak.spring.datafixtures.DataFixtureType.values;

class DataFixtureLoaderServiceTest {

  @Test
  void should_execute_data_fixtures_in_predefined_order() {
    AtomicInteger sharedReference = new AtomicInteger();

    OrderableGenericCountableDataFixture dictionaryDataFixture =
        new OrderableGenericCountableDataFixture(DICTIONARY, sharedReference);
    OrderableGenericCountableDataFixture testDataFixture =
        new OrderableGenericCountableDataFixture(TEST, sharedReference);
    OrderableGenericCountableDataFixture demoDataFixture =
        new OrderableGenericCountableDataFixture(DEMO, sharedReference);
    OrderableGenericCountableDataFixture performanceDataFixtureOne =
        new OrderableGenericCountableDataFixture(PERFORMANCE, sharedReference);
    OrderableGenericCountableDataFixture performanceDataFixtureTwo =
        new OrderableGenericCountableDataFixture(PERFORMANCE, sharedReference);

    List<DataFixture> fixturesShuffled =
        Arrays.asList(
            performanceDataFixtureOne,
            performanceDataFixtureTwo,
            testDataFixture,
            dictionaryDataFixture,
            demoDataFixture);

    DataFixtureLoaderService dataFixtureLoaderService =
        new DataFixtureLoaderService(fixturesShuffled);

    Set<DataFixtureType> allTypes = new HashSet(Arrays.asList(values()));
    dataFixtureLoaderService.applyByTypesMatching(allTypes);

    fixturesShuffled.stream()
        .forEach(
            f ->
                assertThat(((OrderableGenericCountableDataFixture) f).getCallCount()).isEqualTo(1));

    assertThat(dictionaryDataFixture.getOrder()).isEqualTo(1);
    assertThat(testDataFixture.getOrder()).isEqualTo(2);
    assertThat(demoDataFixture.getOrder()).isEqualTo(3);
    assertThat(performanceDataFixtureOne.getOrder()).isEqualTo(4);
    assertThat(performanceDataFixtureTwo.getOrder()).isEqualTo(5);
  }

  @Test
  void should_not_execute_not_applicable_fixtures_in_predefined_order() {
    GenericCountableDataFixture dictionaryDataFixture =
        new GenericCountableDataFixture(DICTIONARY, false);
    GenericCountableDataFixture testDataFixture = new GenericCountableDataFixture(TEST, true);

    DataFixtureLoaderService dataFixtureLoaderService =
        new DataFixtureLoaderService(Arrays.asList(dictionaryDataFixture, testDataFixture));

    Set<DataFixtureType> allTypes = new HashSet(Arrays.asList(values()));
    dataFixtureLoaderService.applyByTypesMatching(allTypes);

    assertThat(dictionaryDataFixture.getCallCount()).isEqualTo(0);
    assertThat(testDataFixture.getCallCount()).isEqualTo(1);
  }
}
