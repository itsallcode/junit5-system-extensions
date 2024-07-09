
package org.itsallcode.junit.sysextensions;

import static org.itsallcode.junit.sysextensions.AssertExit.assertExit;
import static org.itsallcode.junit.sysextensions.AssertExit.assertExitWithStatus;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnJre;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.api.extension.ExtendWith;

@DisabledOnJre(value = {
        JRE.JAVA_21 }, disabledReason = "The Security Manager is deprecated and will be removed in a future release")
@ExtendWith(ExitGuard.class)
class TestSystemExit
{
    @Test
    void testSystemExit()
    {
        assertExit(() -> System.exit(1));
    }

    @Test
    void testSystemExitMissingThrowsAssertError()
    {
        try
        {
            assertExit(() -> {
                // intentionally empty
            });
        }
        catch (final AssertionError assertionError)
        {
            assertMissingExitMessage(assertionError);
            return;
        }
        fail("Code sequence where exit was expected did not exit, no assertion error was raised.");
    }

    private void assertMissingExitMessage(final AssertionError assertionError)
    {
        assertEquals("Lambda did not cause a system exit as expected.", assertionError.getMessage());
    }

    @Test
    void testSystemExitWithStatus()
    {
        assertExitWithStatus(1, () -> System.exit(1));
    }

    @Test
    void testSystemExitWithUnexpectedStatusThrowsAssertionError()
    {
        try
        {
            assertExitWithStatus(0, () -> System.exit(1));
        }
        catch (final AssertionError assertionError)
        {
            return;
        }
        fail("Code sequence where certain exit status was expected exited with different status.");
    }

    @Test
    void testSystemWithStatusMissingExitThrowsAssertError()
    {
        try
        {
            assertExitWithStatus(0, () -> {
                // intentionally empty
            });
        }
        catch (final AssertionError assertionError)
        {
            assertMissingExitMessage(assertionError);
            return;
        }
        fail("Code sequence where exit was expected did not exit, no assertion error was raised.");
    }
}
