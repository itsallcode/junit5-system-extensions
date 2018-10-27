package org.itsallcode.io;

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

import java.io.IOException;

/**
 * This class is thrown when internal errors occur in a
 * {@link CapturingOutputStream}.
 */
public class CapturingOutputStreamException extends RuntimeException
{
    private static final long serialVersionUID = 15647832578068279L;

    /**
     * Create an new instance of a {@link CapturingOutputStreamException}
     *
     * @param message error message
     * @param cause   root cause
     */
    public CapturingOutputStreamException(final String message, final IOException cause)
    {
        super(message, cause);
    }
}
