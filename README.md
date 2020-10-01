# Spring Boot Data Fixtures library
[![Build Status](https://travis-ci.com/piotrpolak/spring-boot-data-fixtures.svg?branch=master)](https://travis-ci.com/piotrpolak/spring-boot-data-fixtures)
[![codecov](https://codecov.io/gh/piotrpolak/spring-boot-data-fixtures/branch/master/graph/badge.svg?token=MC4ZZAQCTJ)](https://codecov.io/gh/piotrpolak/spring-boot-data-fixtures/)


A generic mechanism to load data fixtures upon application startup.

## Usage

Data fixtures are defined as beans implementing the `DataFixture` interface. Example:

```java
@Component
public class InitialDataFixture implements DataFixture {

    private final SomeRepository someRepository;

    // ...

    /**
     * Defines the fixture type. Fixtures are loaded in the order defined by DataFixtureType enum
     * ordinals.
     */
    @Override
    public DataFixtureType getType() {
      return DataFixtureType.DICTIONARY;
    }

    /**
     * Tells whether the fixture is eligible for application. In most cases a fixture is executed upon
     * the fist application startup only.
     */
    @Override
    public boolean shouldBeApplied() {
      return someRepository.isEmpty();
    }

    /**
     * The actual application of the fixture. Assuming data fixtures are registered as beans, this can
     * contain a call to other services and/or repositories.
     */
    @Override
    public void apply() {
      someRepository.save(new SomeEntity());
    }
}
```

### Fixture types

A fixture must define one of the following types:

| Data fixture type | Description                                                                                             |
|-------------------|---------------------------------------------------------------------------------------------------------|
| DICTIONARY        | Initial data such as mandatory dictionaries, initial accounts etc.                                      |
| TEST              | Data used in integration tests.                                                                         |
| DEMO              | Data used for demonstration and manual testing purposes. Should describe representative demo scenarios. |
| PERFORMANCE       | Large performance data sets. Usually generated using loops.                                             |

### Fixture application order

Application can define many fixtures of the same type - defining fixtures per domain is a common practice and a great
way to keep the code decoupled.

The fixtures are loaded in the following order `DICTIONARY` -> `TEST` -> `DEMO` -> `PERFORMANCE`.
If there are more fixtures of the same type, their order can be manually arranged using `@Order` annotation.

Fixtures from the example below will be applied in the following order:
 `InitialCountriesDataFixture` -> `InitialCountriesDataFixture` -> `DemoProductsDataFixture`

```java
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class InitialCountriesDataFixture implements DataFixture {

    @Override
    public DataFixtureType getType() {
      return DataFixtureType.DICTIONARY;
    }
    // ...
}

@Component
@Order(Ordered.LOWEST_PRECEDENCE)
public class InitialCountriesDataFixture implements DataFixture {

    @Override
    public DataFixtureType getType() {
      return DataFixtureType.DICTIONARY;
    }
    // ...
}

@Component
@Order(Ordered.HIGHEST_PRECEDENCE) // Doesn't really matter if there is a single fixture of the demo type
public class DemoProductsDataFixture implements DataFixture {

    @Override
    public DataFixtureType getType() {
      return DataFixtureType.DEMO;
    }
    // ...
}
```

## Configuration options

| Property name                           | Description                                                  | Default    | Allowed values                                                       |
|-----------------------------------------|--------------------------------------------------------------|------------|----------------------------------------------------------------------|
| `ro.polak.spring.data-fixtures.enabled` | Turns on and off the data features mechanism                 | true       | true, false                                                          |
| `ro.polak.spring.data-fixtures.types`   | Specifies the types fixture types to be loaded automatically | DICTIONARY | DICTIONARY, TEST, DEMO, PERFORMANCE and any combination of the above |

## Installation

Maven

```xml
<dependency>
    <groupId>ro.polak</groupId>
    <artifactId>spring-boot-data-fixtures</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```

## How it works
- https://docs.spring.io/spring-boot/docs/current/reference/html/using-spring-boot.html#using-boot-auto-configuration

## License

The project is licensed under MIT license.