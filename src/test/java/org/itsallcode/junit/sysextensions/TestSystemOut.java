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

import org.itsallcode.io.Capturable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(SystemOutGuard.class)
class TestSystemOut
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
        System.out.print(ignored);
        stream.capture();
        final String expected = "This text must be captured." + System.lineSeparator();
        System.out.print(expected);
        assertEquals(stream.getCapturedData(), expected);
    }

    @Test
    void testCaptureB(final Capturable stream)
    {
        final String ignored = "We repeat the test to see if resetting works." + System.lineSeparator();
        System.out.print(ignored);
        stream.capture();
        final String expected = "Again: this must be captured." + System.lineSeparator();
        System.out.print(expected);
        assertEquals(stream.getCapturedData(), expected);
    }
}
