package org.itsallcode.junit.sysextensions;

/*-
 * #%L
 * JUnit5 System Extensions
 * %%
 * Copyright (C) 2018 itsallcode.org
 * %%
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the Eclipse
 * Public License, v. 2.0 are satisfied: GNU General Public License, version 2
 * with the GNU Classpath Exception which is
 * available at https://www.gnu.org/software/classpath/license.html.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 * #L%
 */

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
            assertEquals(expectedExitCode, e.getExitStatus(), "Expected exit status code");
            return;
        }
        failMissingExit();
    }
}
