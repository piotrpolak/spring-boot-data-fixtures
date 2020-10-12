package test.ro.polak.springboot.datafixtures.samples;

import java.util.concurrent.atomic.AtomicInteger;
import ro.polak.springboot.datafixtures.DataFixtureSet;

public class OrderableGenericCountableDataFixture extends GenericCountableDataFixture {

  private final AtomicInteger orderSharedReference;
  private int order = -1;

  public OrderableGenericCountableDataFixture(
      final DataFixtureSet set, final AtomicInteger orderSharedReference) {
    super(set);
    this.orderSharedReference = orderSharedReference;
  }

  @Override
  public void load() {
    super.load();
    order = orderSharedReference.incrementAndGet();
  }

  public int getOrder() {
    return order;
  }
}
