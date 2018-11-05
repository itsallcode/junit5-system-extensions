package org.itsallcode.junit.sysextensions;

/*-
 * #%L
 * JUnit5 System Extensions
 * %%
 * Copyright (C) 2018 itsallcode.org
 * %%
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution and is available at
 *
 * http://www.eclipse.org/legal/epl-v20.html
 * #L%
 */

import static org.itsallcode.junit.sysextensions.AssertExit.assertExit;
import static org.itsallcode.junit.sysextensions.AssertExit.assertExitWithStatus;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

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

    @Test
    void testSystemExitMissingThrowsAssertError()
    {
        try
        {
            assertExit(() -> {
                // intentionally empty
            });
        } catch (final AssertionError assertionError)
        {
            assertMissingExitMessage(assertionError);
            return;
        }
        fail("Code sequence where exit was expected did not exit, no assertion error was raised.");
    }

    private void assertMissingExitMessage(final AssertionError assertionError)
    {
        assertEquals(assertionError.getMessage(), "Lambda did not cause a system exit as expected.");
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
        } catch (final AssertionError assertionError)
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
        } catch (final AssertionError assertionError)
        {
            assertMissingExitMessage(assertionError);
            return;
        }
        fail("Code sequence where exit was expected did not exit, no assertion error was raised.");
    }
}