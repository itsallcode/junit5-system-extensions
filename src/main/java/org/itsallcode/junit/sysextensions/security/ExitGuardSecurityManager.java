package org.itsallcode.junit.sysextensions.security;

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