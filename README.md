# junit5-system-extensions (J5SE)

This project provides a set of JUnit 5 extension that allow testing behavior related to functions related to `java.lang.System` (e.g. asserting exit status codes).

[![Build](https://github.com/itsallcode/junit5-system-extensions/actions/workflows/build.yml/badge.svg)](https://github.com/itsallcode/junit5-system-extensions/actions/workflows/build.yml)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=org.itsallcode%3Ajunit5-system-extensions&metric=alert_status)](https://sonarcloud.io/dashboard?id=org.itsallcode%3Ajunit5-system-extensions)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=org.itsallcode%3Ajunit5-system-extensions&metric=coverage)](https://sonarcloud.io/dashboard?id=org.itsallcode%3Ajunit5-system-extensions)
[![Maven Central](https://img.shields.io/maven-central/v/org.itsallcode/junit5-system-extensions)](https://search.maven.org/artifact/org.itsallcode/junit5-system-extensions)

## Acknowledgments

The extensions in this project were inspired by a set of JUnit4 rules called "[System Rules](https://stefanbirkner.github.io/system-rules/)" which were written by Stefan Brikner and licensed under the Common Public License 1.0 (CPL).

## Runtime Dependencies

Starting with version 1.2.0 `junit5-system-extensions` requires Java 11 to compile and at runtime. If your project requires Java 8, please use version 1.1.0.

| Dependency                                                                   | Purpose                                                | License                       |
-------------------------------------------------------------------------------|--------------------------------------------------------|--------------------------------
| [JUnit5](https://junit.org/junit5/)                                          | Unit test framework                                    | Eclipse Public License v2.0   |

## Usage

### Asserting `System.exit(int)` Calls

To trap and check calls to `System.exit(int)` follow these steps:

1. Extend the test class with the class `ExitGuard`
2. Use `AssertExit.assertExit(Runnable)` or `AssertExit.assertExitWithStatus(int, Runnable)` to check for exit calls

Example:

```java
import static org.itsallcode.junit.AssertExit.assertExit;

import org.itsallcode.junit.sysextensions.ExitGuard;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(ExitGuard.class)
class TestSystemExit
{
    @Test
    void testSystemExit()
    {
        assertExit(() -> System.exit(1));
    }
}
```

The `ExitGuard` temporarily replaces the existing security manager.

From version 1.2.0 on if a security guard existed before, it serves as a delegate for all security checks with the exception of the `checkExit`.

**Warning:** The JREs Security Manager used by `ExitGuard` is deprecated and is not supported by Java 21 and later. It still works with Java 17 but logs the following warning:

```
WARNING: A terminally deprecated method in java.lang.System has been called
WARNING: System::setSecurityManager has been called by ...
WARNING: Please consider reporting this to the maintainers of ...
WARNING: System::setSecurityManager will be removed in a future release
```

## Asserting Data Sent to `System.out`

To capture data sent to `System.out`, follow these steps:

1. Extend the test class with `SystemOutGuard`
2. Add a parameter of type `Capturable` to the test method (or the before-all-method)
3. Activate capturing on the stream
4. Run code under test
5. Check captured data

Example:

```java
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.itsallcode.io.Capturable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(SystemOutGuard.class)
class TestSystemOut
{
    @Test
    void testCapture(final Capturable stream)
    {
        stream.capture();
        final String expected = "This text must be captured.";
        System.out.print(expected);
        assertEquals(stream.getCapturedData(), expected);
    }
}
```

To mute the output (i.e. don't forward output to `System.out` / `System.err`) call `stream.captureMuted()` instead of `stream.capture()`. This can be useful to speed up unit tests.

## Asserting Data Sent to `System.err`

Capturing data sent to `System.err` works in the exact same way as in the [`System.out` case](#asserting-data-sent-to-system-out). The only difference is that you need to extend the test class with the `SystemErrGuard`.

## Contributing, Feature Requests and Error Reporting

Please check our [contribution guide](.github/CONTRIBUTING.md) to learn how you can help with the project, report errors or request features.

## Changelog

[Changelog](doc/changes/changelog.md)

## Development

### Build Time Dependencies

The list below show all build time dependencies in alphabetical order. Note that except the [Maven](https://maven.apache.org/) build tool all required modules are downloaded automatically by Maven.

| Dependency                                                                       | Purpose                                                | License                       |
-----------------------------------------------------------------------------------|--------------------------------------------------------|--------------------------------
| [Apache Maven](https://maven.apache.org/)                                        | Build tool                                             | Apache License 2.0            |
| [License Maven Plugin](https://www.mojohaus.org/license-maven-plugin/)           | Add licenses to source files automatically             | GNU Public License 3.0        |
| [Maven Compiler Plugin](https://maven.apache.org/plugins/maven-compiler-plugin/) | Maven provided and controlled Java compiler            | Apache License 2.0            |
| [Maven Source Plugin](https://maven.apache.org/plugins/maven-source-plugin/)     | Create Source JAR packages                             | Apache License 2.0            |
| [Maven JavaDoc Plugin](https://maven.apache.org/plugins/maven-javadoc-plugin/)   | Create JavaDoc JAR packages                            | Apache License 2.0            |
| [Mockito](https://site.mockito.org/)                                             | Mocking framework                                      | MIT License                   |

### Essential Build Steps

* `git clone https://github.com/itsallcode/junit5-system-extensions.git`
* Run `mvn test` to run unit tests.
* Run `mvn package` to create the JAR file.

## Run local sonar analysis

```bash
mvn clean org.jacoco:jacoco-maven-plugin:prepare-agent package sonar:sonar \
    -Dsonar.token=[token]
```

See analysis results at https://sonarcloud.io/dashboard?id=org.itsallcode%3Ajunit5-system-extensions

## Check for updated dependencies / plugins

```bash
mvn --update-snapshots versions:display-dependency-updates versions:display-plugin-updates
```

### Publishing to MavenCentral
#### Prepare the Release

1. Checkout the `main` branch.
2. Update version in `pom.xml` and changelog.
3. Commit and push changes.
4. Create a new pull request, have it reviewed and merged to `main`.

### Perform the Release

1. Start the release workflow
  * Run command `gh workflow run release.yml --repo itsallcode/junit5-system-extensions --ref main`
  * or go to [GitHub Actions](https://github.com/itsallcode/junit5-system-extensions/actions/workflows/release.yml) and start the `release.yml` workflow on branch `main`.
2. Update title and description of the newly created [GitHub release](https://github.com/itsallcode/junit5-system-extensions/releases).
3. After some time the release will be available at [Maven Central](https://repo1.maven.org/maven2/org/itsallcode/junit5-system-extensions/).
