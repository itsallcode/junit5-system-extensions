# junit5-system-extensions (J5SE)

This project provides a set of JUnit 5 extension that allow testing behavior related to functions related to `java.lang.System` (e.g. asserting exit status codes).

[![Build Status](https://travis-ci.com/itsallcode/junit5-system-extensions.svg?branch=master)](https://travis-ci.com/itsallcode/junit5-system-extensions)
[![Download](https://api.bintray.com/packages/itsallcode/itsallcode/junit5-system-extensions/images/download.svg)](https://bintray.com/itsallcode/itsallcode/junit5-system-extensions/_latestVersion)

## Acknowledgments

The extensions in this project were inspired by a set of JUnit4 rules called "[System Rules](https://stefanbirkner.github.io/system-rules/)" which were written by Stefan Brikner and licensed under the Common Public License 1.0 (CPL).

## Runtime Dependencies

| Dependency                                                                   | Purpose                                                | License                       |
-------------------------------------------------------------------------------|--------------------------------------------------------|--------------------------------
| [JUnit5](https://junit.org/junit5/)                                          | Unit test framework                                    | Eclipse Public License v2.0   |

## Usage

### Asserting `System.exit(int)` Calls

To trap and check calls to `System.exit(int)` follow these steps:

1. Extend the test class with the class `ExitGuard`
2. Use `AssertExit.assertExit(Runnable)` or `AssertExit.assertExit(int, Runnable)` to check for exit calls

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

Note that in order to be able to trap system exit, the `ExitGuard` temporarily replaces the existing security manager (if any).

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

### Publishing to JCenter

1. Add the following to your `~/.m2/settings.xml`:

    ```xml
    <servers>
        <server>
            <id>itsallcode-maven-repo</id>
            <username>[bintray-username]</username>
            <password>[bintray-api-key]</password>
        </server>
    </servers>
    ```

1. Checkout the `develop` branch.
1. Update version in `pom.xml` and `README.md`, commit and push.
1. Run command

    ```bash
    mvn deploy
    ```
1. Merge to `master` branch
1. Create a [release](https://github.com/itsallcode/junit5-system-extensions/releases) of the `master` branch on GitHub.
1. Sign in at [bintray.com](https://bintray.com)
1. Go to the [Bintray project page](https://bintray.com/itsallcode/itsallcode/junit5-system-extensions)
1. Publish to Maven Central by clicking the "Sync" button at https://bintray.com/itsallcode/itsallcode/junit5-system-extensions#central. After some time the new version will appear at https://repo1.maven.org/maven2/org/itsallcode/junit5-system-extensions/.
