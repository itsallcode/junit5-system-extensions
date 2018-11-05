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