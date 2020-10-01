package test.ro.polak.spring.datafixtures.samples;

import java.util.concurrent.atomic.AtomicInteger;
import ro.polak.spring.datafixtures.DataFixtureType;

public class OrderableGenericCountableDataFixture extends GenericCountableDataFixture {

  private final AtomicInteger orderSharedReference;
  private int order = -1;

  public OrderableGenericCountableDataFixture(
      final DataFixtureType type, final AtomicInteger orderSharedReference) {
    super(type);
    this.orderSharedReference = orderSharedReference;
  }

  @Override
  public void apply() {
    super.apply();
    order = orderSharedReference.incrementAndGet();
  }

  public int getOrder() {
    return order;
  }
}
