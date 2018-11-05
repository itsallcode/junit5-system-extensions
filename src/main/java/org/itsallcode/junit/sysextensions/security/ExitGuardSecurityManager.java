package org.itsallcode.junit.sysextensions.security;

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

import java.security.Permission;

public class ExitGuardSecurityManager extends SecurityManager
{
    private boolean trapExit = false;

    @Override
    public synchronized void checkExit(final int status)
    {
        if (this.trapExit)
        {
            this.trapExit = false;
            throw new ExitTrapException(this.getClass().getSimpleName() + " intercepted a System.exit(" + status + ").",
                    status);
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