package org.itsallcode.junit.sysextensions.security;

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
