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

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.itsallcode.io.Capturable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(SystemErrGuard.class)
class TestSystemErr
{
    @Test
    void testCapture(final Capturable stream)
    {
        stream.capture();
        assertEquals(stream.getCapturedData(), "");
    }

    @Test
    void testCaptureA(final Capturable stream)
    {
        final String ignored = "Ignore this text here." + System.lineSeparator();
        System.err.print(ignored);
        stream.capture();
        final String expected = "This text must be captured." + System.lineSeparator();
        System.err.print(expected);
        assertEquals(stream.getCapturedData(), expected);
    }

    @Test
    void testCaptureB(final Capturable stream)
    {
        final String ignored = "We repeat the test to see if resetting works." + System.lineSeparator();
        System.err.print(ignored);
        stream.capture();
        final String expected = "Again: this must be captured." + System.lineSeparator();
        System.err.print(expected);
        assertEquals(stream.getCapturedData(), expected);
    }
}
