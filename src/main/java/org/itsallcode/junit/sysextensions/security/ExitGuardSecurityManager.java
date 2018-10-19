package org.itsallcode.junit.sysextensions.security;

import java.security.Permission;

/**
 * This class implements a {@link SecurityManager} with the purpose to intercept
 * calls to <code>System.exit(int)</code> in order to retrieve the exit status
 * code (an integer returned to the calling program).
 */
public class ExitGuardSecurityManager extends SecurityManager
{
    private boolean trapExit = false;

    @Override
    public void checkExit(final int status)
    {
        if (this.trapExit)
        {
            this.trapExit = false;
            throw new ExitSecurityException(
                    this.getClass().getSimpleName() + " intercepted a System.exit(" + status + ").", status);
        }
    }

    @Override
    public void checkPermission(final Permission perm)
    {
        // intentionally empty
    }

    public void trapExit(final boolean trapExit)
    {
        this.trapExit = trapExit;
    }
}