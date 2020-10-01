# Spring Boot Data Fixtures library

A generic mechanism to load data fixtures upon application startup.

## Configuration options

| Property name                         | Description                                                  | Default | Allowed values                                                       |
|---------------------------------------|--------------------------------------------------------------|---------|----------------------------------------------------------------------|
| ro.polak.spring.data-fixtures.enabled | Turns on and off the data features mechanism                 | false   | true, false                                                          |
| ro.polak.spring.data-fixtures.types   | Specifies the types fixture types to be loaded automatically | Empty   | DICTIONARY, TEST, DEMO, PERFORMANCE and any combination of the above |

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