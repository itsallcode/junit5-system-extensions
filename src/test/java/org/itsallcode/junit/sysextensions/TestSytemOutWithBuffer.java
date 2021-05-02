package org.itsallcode.junit.sysextensions;

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

    @SuppressWarnings("squid:S2699")
    @Test
    void testGetCapturedRegular(final Capturable stream) throws IOException
    {
        // This test is necessary to see forEach behavior.
        // It is intentionally empty.
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
