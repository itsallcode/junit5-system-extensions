package org.itsallcode.junit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.itsallcode.junit.sysextensions.ExitGuard;
import org.itsallcode.junit.sysextensions.security.ExitTrapException;

/**
 * This class implements assert methods for checking exit codes.
 *
 * <p>
 * Note that to use the assert in this class successfully, you need to first
 * extend the JUnit5 test it is used in with an {@link ExitGuard}.
 * </p>
 */
public final class AssertExit
{
    private AssertExit()
    {
        // prevent instantiation
    }

    /**
     * Assert that the code in the given lambda called {@link System#exit(int)}.
     *
     * @param runnable lambda expression that is expected to call
     *                 {@link System#exit(int)}
     */
    public static void assertExit(final Runnable runnable)
    {
        try
        {
            runnable.run();
        } catch (final ExitTrapException e)
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
     * Assert that the code in the given lambda called {@link System#exit(int)} with
     * a the expected exit code.
     *
     * @param expectedExitCode expected exit status code
     * @param runnable         lambda expression that is expected to call
     *                         {@link System#exit(int)}
     */
    public static void assertExitWithStatus(final int expectedExitCode, final Runnable runnable)
    {
        try
        {
            runnable.run();
        } catch (final ExitTrapException e)
        {
            assertEquals("Expected exit status code", expectedExitCode, e.getExitStatus());
            return;
        }
        failMissingExit();
    }
}