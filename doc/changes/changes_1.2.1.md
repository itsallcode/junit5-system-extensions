# JUnit5 System Extensions 1.2.1, released 2024-07-09

Code name: Sonar smell fixes for 1.2.0

## Summary

Version 1.2.1 is a service release that fixed SONAR code smells and adds tests with Java 17 and Java 21.

## Bugfixes

* [#27](https://github.com/itsallcode/junit5-system-extensions/issues/27): Fixed SONAR code smells of version 1.2.0.

## Refactoring

* [PR #67](https://github.com/itsallcode/junit5-system-extensions/pull/66): Upgrade dependencies
* [PR #69](https://github.com/itsallcode/junit5-system-extensions/pull/69): Remove license header from sources
* [PR #72](https://github.com/itsallcode/junit5-system-extensions/pull/72): Adapt to Java 17 and 21, add JavaDoc

## Development process

* The `develop` branch was renamed to `main`, `master` was deleted.

## Deprecation Warning

The JREs Security Manager used by `ExitGuard` is deprecated and is not supported by Java 21 and later.
