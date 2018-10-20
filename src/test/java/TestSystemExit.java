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
import static org.itsallcode.junit.sysextensions.AssertExit.assertExit;
import static org.itsallcode.junit.sysextensions.AssertExit.assertExitWithStatus;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

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
