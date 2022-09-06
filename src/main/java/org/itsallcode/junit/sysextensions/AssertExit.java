package org.itsallcode.junit.sysextensions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.itsallcode.junit.sysextensions.security.ExitTrapException;

public final class AssertExit
{
    private AssertExit()
    {
        // prevent instantiation
    }

    /**
     * Assert that the code in the given lambda called {@link System#exit(int)}.
     *
     * @param runnable
     *            lambda expression that is expected to call
     *            {@link System#exit(int)}
     */
    public static void assertExit(final Runnable runnable)
    {
        try
        {
            runnable.run();
        }
        catch (final ExitTrapException e)
        {
            return;
        }
        failMissingExit();
    }

    private static void failMissingExit()
    {
        fail("Lambda did not cause a system exit as expected.");
    }

    /**
     * Assert that the code in the given lambda called {@link System#exit(int)}
     * with a the expected exit code.
     *
     * @param expectedExitCode
     *            expected exit status code
     * @param runnable
     *            lambda expression that is expected to call
     *            {@link System#exit(int)}
     */
    public static void assertExitWithStatus(final int expectedExitCode, final Runnable runnable)
    {
        try
        {
            runnable.run();
        }
        catch (final ExitTrapException e)
        {
            assertEquals(expectedExitCode, e.getExitStatus(), "Expected exit status code");
            return;
        }
        failMissingExit();
    }
}
