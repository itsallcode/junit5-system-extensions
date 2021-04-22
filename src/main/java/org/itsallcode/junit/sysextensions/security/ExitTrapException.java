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

public class ExitTrapException extends SecurityException
{
    private static final long serialVersionUID = 3483205912039194022L;
    /** @serial */
    private final int status;

    public ExitTrapException(final String message, final int status)
    {
        super(message);
        this.status = status;
    }

    public int getExitStatus()
    {
        return this.status;
    }
}
