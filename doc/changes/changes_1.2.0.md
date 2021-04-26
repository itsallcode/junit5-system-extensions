# JUnit5 System Extensions 1.2.0, released 2021-04-26

Code name: Mute output

## Summary

When trapping `System.exit` calls, the `ExitGuardSecurityManager` now doesn't simply replace an existing security manager anymore. Instead it uses the existing one as a delegate for all checks except the exit check.

This way applications that require a security manager don't change their behavior.

## Features

* [#23](https://github.com/itsallcode/junit5-system-extensions/issues/23): Delegate security manager calls to the original security manager

## Refactoring

* [#21](https://github.com/itsallcode/junit5-system-extensions/issues/21): Migrate to Maven Central
