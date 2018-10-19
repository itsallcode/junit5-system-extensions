package org.itsallcode.junit.sysextensions.security;

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

import java.security.Permission;

/**
 * This class implements a {@link SecurityManager} with the purpose to intercept
 * calls to {@link System#exit(int)} in order to retrieve the exit status code
 * (an integer returned to the calling program).
 *
 * <p>
 * To avoid trapping exits that are not related to tests the trap can be
 * switched on and off.
 * </p>
 *
 * <p>
 * The {@link #checkExit(int)} method throws an {@link ExitTrapException} in
 * case an exit call was caught. This exception also contains the exit status.
 * </p>
 */
public class ExitGuardSecurityManager extends SecurityManager
{
    private boolean trapExit = false;

    @Override
    public synchronized void checkExit(final int status)
    {
        if (this.trapExit)
        {
            this.trapExit = false;
            throw new ExitTrapException(
                    this.getClass().getSimpleName() + " intercepted a System.exit(" + status + ").", status);
        }
    }

    @Override
    public void checkPermission(final Permission perm)
    {
        // intentionally empty
    }

    /**
     * Switch trapping {@link System#exit(int)} calls on or off.
     *
     * @param trapExit set to <code>true</code> if the
     *                 {@link ExitGuardSecurityManager} should trap exit calls
     */
    public void trapExit(final boolean trapExit)
    {
        this.trapExit = trapExit;
    }
}
