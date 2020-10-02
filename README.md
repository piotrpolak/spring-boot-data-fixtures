# Spring Boot Data Fixtures starter
[![Build Status](https://travis-ci.com/piotrpolak/spring-boot-data-fixtures.svg?branch=master)](https://travis-ci.com/piotrpolak/spring-boot-data-fixtures)
[![codecov](https://codecov.io/gh/piotrpolak/spring-boot-data-fixtures/branch/master/graph/badge.svg?token=MC4ZZAQCTJ)](https://codecov.io/gh/piotrpolak/spring-boot-data-fixtures/)

Loads initial data upon application startup. The starter benefits from Spring Boot
[Auto-configuration](https://docs.spring.io/spring-boot/docs/current/reference/html/using-spring-boot.html#using-boot-auto-configuration) feature
and it is automatically enabled once it added to classpath.

## Usage

Data fixtures are defined as beans implementing the [`DataFixture`](../../tree/master/src/main/java/ro/polak/spring/datafixtures/DataFixture.java)
interface. They can generate and load data using services, repositories or just execute plain SQL.

Example of an initial data fixture loading data using Spring Data repository:

```java
@Component
public class InitialDataFixture implements DataFixture {

    private final LanguageRepository languageRepository;

    // ...

    /**
     * Defines the fixture set. Fixtures are loaded in the order defined by DataFixtureSet enum
     * ordinals.
     */
    @Override
    public DataFixtureSet getSet() {
      return DataFixtureSet.DICTIONARY;
    }

    /**
     * Tells whether the fixture is eligible to be applied. In most cases a fixture is executed
     * upon the fist application startup only.
     */
    @Override
    public boolean shouldBeApplied() {
      return languageRepository.isEmpty();
    }

    /**
     * The actual application of the fixture. Assuming that data fixtures are registered as beans,
     * this can contain a call to other services and/or repositories.
     */
    @Override
    public void apply() {
      languageRepository.saveAll(Arrays.asList(new Language("en-US"), new Language("pl-PL")));
    }
}
```

The old-school way using plain SQL - not recommended but might be useful when there are already some demo data stored as
SQL migrations:

```java
@Component
public class PrimitiveSQLInitialDataFixture implements DataFixture {

    private final JdbcTemplate jdbcTemplate;

    // ...

    /**
     * The actual application of the fixture. Assuming that data fixtures are registered as beans,
     * this can contain a call to other services and/or repositories.
     */
    @Override
    public void apply() {
      try {
          ClassPathResource resource = new ClassPathResource("countries.sql").getInputStream();
          String rawSql = StreamUtils.copyToString(resource, Charset.defaultCharset());
          jdbcTemplate.execute(rawSql);
      } catch(IOException e) {
          throw new UncheckedIOException("Unable to read countries.sql", e);
      }
    }
}
```

### Fixture data sets

A fixture must belong to one of the following sets:

| Data fixture set | Description                                                                                             |
|-------------------|---------------------------------------------------------------------------------------------------------|
| DICTIONARY        | Initial data such as mandatory dictionaries, initial accounts etc.                                      |
| TEST              | Data used in integration tests.                                                                         |
| DEMO              | Data used for demonstration and manual testing purposes. Should describe representative demo scenarios. |
| PERFORMANCE       | Large performance data sets. Usually generated using loops.                                             |

### Fixture application order

Application can define many fixtures of the same set - defining fixtures per domain is a common practice, and a great
way to keep the code decoupled.

The fixtures are loaded in the following order `DICTIONARY` -> `TEST` -> `DEMO` -> `PERFORMANCE`.
In case when there are more fixtures of the same set, their order can be manually arranged using the
[`@Order`](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/annotation/Order.html)
annotation.

Fixtures from the example below will be applied in the following order:
 `InitialCountriesDataFixture` -> `InitialCountriesDataFixture` -> `DemoProductsDataFixture`

```java
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class InitialCountriesDataFixture implements DataFixture {

    @Override
    public DataFixtureSet getSet() {
      return DataFixtureSet.DICTIONARY;
    }
    // ...
}

@Component
@Order(Ordered.LOWEST_PRECEDENCE)
public class InitialCountriesDataFixture implements DataFixture {

    @Override
    public DataFixtureSet getSet() {
      return DataFixtureSet.DICTIONARY;
    }
    // ...
}

@Component
@Order(Ordered.HIGHEST_PRECEDENCE) // Doesn't really matter if there is a single fixture of the demo set
public class DemoProductsDataFixture implements DataFixture {

    @Override
    public DataFixtureSet getSet() {
      return DataFixtureSet.DEMO;
    }
    // ...
}
```

## Configuration options

| Property name                           | Description                                                  | Default      |
|-----------------------------------------|--------------------------------------------------------------|--------------|
| `ro.polak.spring.data-fixtures.enabled` | Turns on and off the data features mechanism                 | true         |
| `ro.polak.spring.data-fixtures.sets`   | Specifies the sets fixture sets to be loaded automatically | `DICTIONARY` |

In a typical scenario
- production environment applies `DICTIONARY` fixtures only
- integration tests environment applies `DICTIONARY` and `TEST` fixtures or just `DICTIONARY`
  (under the assumption that each test populates and cleans up the database)
- test/demo environment applies `DICTIONARY` and `DEMO` fixtures
- performance test environment applies `DICTIONARY` and `PERFORMANCE` fixtures

## Installation

### Maven

```xml
<repositories>
    <repository>
      <id>ossrh</id>
      <url>https://oss.sonaset.org/content/repositories/snapshots</url>
    </repository>
</repositories>
```

```xml
<dependency>
    <groupId>ro.polak</groupId>
    <artifactId>spring-boot-data-fixtures</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```

### Gradle

```groovy
repositories {
    maven {
        url "https://oss.sonaset.org/content/repositories/snapshots"
    }
}
```

```groovy
implementation 'ro.polak:spring-boot-data-fixtures:0.0.1-SNAPSHOT'
```

## License

The project is licensed under MIT license.

## Deploying snapshots (signed)

```bash
mvn clean deploy -P deploy
```