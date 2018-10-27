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

import java.io.IOException;
import java.io.PrintStream;

import org.itsallcode.io.Capturable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(SystemOutGuard.class)
class TestSytemOutWithBuffer
{
    @BeforeEach
    void beforeEach()
    {
    }

    @Test
    void testGetCapturedRegular(final Capturable stream) throws IOException
    {
        // This test is necessary to see forEach behavior
    }

    @Test
    void testGetCapturedDataWithPrintStreamAroundSystemOut(final Capturable stream) throws IOException
    {
        final PrintStream printStream = new PrintStream(System.out);
        stream.capture();
        final String expected = "Expected";
        printStream.print(expected);
        printStream.flush();
        assertEquals(expected, stream.getCapturedData());
    }
}
