package org.itsallcode.junit;

/*-
 * #%L
 * JUnit5 System Extensions
 * %%
 * Copyright (C) 2016 - 2018 itsallcode.org
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

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
