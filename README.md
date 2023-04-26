# Spring Boot Data Fixtures starter
[![codecov](https://codecov.io/gh/piotrpolak/spring-boot-data-fixtures/branch/master/graph/badge.svg?token=MC4ZZAQCTJ)](https://codecov.io/gh/piotrpolak/spring-boot-data-fixtures/)
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/7611c8703c51493db1a68e18055c8b6f)](https://www.codacy.com/gh/piotrpolak/spring-boot-data-fixtures/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=piotrpolak/spring-boot-data-fixtures&amp;utm_campaign=Badge_Grade)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/ro.polak/spring-boot-data-fixtures/badge.svg)](https://maven-badges.herokuapp.com/maven-central/ro.polak/spring-boot-data-fixtures)

Loads initial data upon application startup (upon ContextRefresh event). The starter benefits from Spring Boot
[Auto-configuration](https://docs.spring.io/spring-boot/docs/current/reference/html/using-spring-boot.html#using-boot-auto-configuration) feature
and it is automatically enabled once it is added to the classpath.

## Usage

Data fixtures are defined as beans implementing the [`DataFixture`](../../tree/master/src/main/java/ro/polak/springboot/datafixtures/DataFixture.java)
interface. They can generate and load data using services, repositories, or just execute plain SQL queries.

Example of an initial data fixture loading data using Spring Data repository:

```java
@Component
public class InitialDataFixture implements DataFixture {

    private final LanguageRepository languageRepository;

    // ...

    /**
     * Defines the fixture set. Fixtures are loaded in the order defined by DataFixtureType enum
     * ordinals.
     *
     * @return data fixture set
     */
    @Override
    public DataFixtureSet getSet() {
      return DataFixtureSet.DICTIONARY;
    }

   /**
    * Tells whether the fixture is eligible to be applied. In most cases a fixture is executed upon
    * the fist application startup only.
    *
    * @return whether the fixture should be applied or not
    */
    @Override
    public boolean canBeLoaded() {
      return languageRepository.size() == 0;
    }

    /**
     * The actual application of the fixture. Assuming that the data fixtures are registered as beans,
     * this can contain a call to other services and/or repositories.
     */
    @Override
    public void load() {
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

    @Override
    public void load() {
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

| Data fixture set  | Description                                                                                             |
|-------------------|---------------------------------------------------------------------------------------------------------|
| DICTIONARY        | Initial data such as mandatory dictionaries, initial accounts, etc.                                     |
| TEST              | Data used in integration tests.                                                                         |
| DEMO              | Data used for demonstration and manual testing purposes. Should describe representative demo scenarios. |
| PERFORMANCE       | Large performance data sets. Usually generated using loops.                                             |

### Fixture load order

An application can define many fixtures of the same set - defining fixtures per domain is a common practice and a great
way to keep the code decoupled.

The fixtures are loaded in the following order: `DICTIONARY` -> `TEST` -> `DEMO` -> `PERFORMANCE`.
In case when there are more fixtures of the same set, their order can be manually arranged using the
[`@Order`](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/annotation/Order.html)
annotation.

Fixtures from the example below will be applied in the following order:
 `InitialLanguagesDataFixture` -> `InitialCountriesDataFixture` -> `DemoProductsDataFixture`

```java
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class InitialLanguagesDataFixture implements DataFixture {

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
// The Order annotation doesn't really matter here since there is a single fixture of the demo set
@Order(Ordered.HIGHEST_PRECEDENCE)
public class DemoProductsDataFixture implements DataFixture {

    @Override
    public DataFixtureSet getSet() {
      return DataFixtureSet.DEMO;
    }
    // ...
}
```

## Configuration options

| Property name           | Description                                                         | Default      |
|-------------------------|---------------------------------------------------------------------|--------------|
| `data-fixtures.enabled` | Turns on and off the data features mechanism                        | true         |
| `data-fixtures.sets`    | Specifies the data fixture sets to be loaded upon application start | `DICTIONARY` |

In a typical scenario

- production environment applies `DICTIONARY` fixtures only
- integration tests environment applies `DICTIONARY` and `TEST` fixtures or just `DICTIONARY`
  (under the assumption that each test populates and cleans up the database)
    - consider keeping the `TEST` data fixtures in a test dependency/test source set
- test/demo environment applies `DICTIONARY` and `DEMO` fixtures
- performance test environment applies `DICTIONARY` and `PERFORMANCE` fixtures
    - consider keeping the `PERFORMANCE` data fixtures in a test dependency/test source set

## Installation

The artifact is published in [Maven Central](https://search.maven.org/artifact/ro.polak/spring-boot-data-fixtures).

### Maven

```xml
<dependency>
    <groupId>ro.polak</groupId>
    <artifactId>spring-boot-data-fixtures</artifactId>
    <version>0.2.0</version>
</dependency>
```

### Gradle

```groovy
implementation 'ro.polak:spring-boot-data-fixtures:0.2.0'
```

### Older versions

For Spring Boot pre `2.7`, use `0.1.0` version.

Version `0.2.0` is compatible with Spring `2.7+` and `3+`.

### Snapshot repositories

#### Maven

```xml
<repositories>
    <repository>
      <id>ossrh</id>
      <url>https://oss.sonatype.org/content/repositories/snapshots/</url>
    </repository>
</repositories>
```

#### Gradle

```groovy
repositories {
    maven {
        url "https://oss.sonaset.org/content/repositories/snapshots"
    }
}
```

## License

The project is licensed under MIT license.

## Deploying snapshots (signed)

```bash
mvn clean deploy -P deploy
```

## Deploying production (signed)

```bash
mvn clean release:clean release:prepare release:perform -P deploy
```
