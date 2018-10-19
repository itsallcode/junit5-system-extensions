# junit5-system-extensions

This project provides a set of JUnit 5 extension that allow testing behavior related to functions related to `java.lang.System` (e.g. asserting exit status codes). 

## Acknowledgments

The extensions in this project were inspired by a set of JUnit4 rules called "[System Rules](https://stefanbirkner.github.io/system-rules/)" which were written by Stefan Brikner and licensed under the Common Public License 1.0 (CPL).

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