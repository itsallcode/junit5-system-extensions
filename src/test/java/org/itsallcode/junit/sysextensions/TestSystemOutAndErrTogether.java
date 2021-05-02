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

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.itsallcode.io.Capturable;
import org.itsallcode.junit.sysextensions.SystemErrGuard.SysErr;
import org.itsallcode.junit.sysextensions.SystemOutGuard.SysOut;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(SystemOutGuard.class)
@ExtendWith(SystemErrGuard.class)
class TestSystemOutAndErrTogether
{
    final static String EXPECTED_OUT = "This goes to STDOUT.";
    final static String EXPECTED_ERR = "This goes to STDERR.";

    @BeforeEach
    void BeforeEach()
    {
        System.out.print("Don't capture this");
        System.err.print("Or this");
    }

    @Test
    void testCaptureSystemOut(@SysOut final Capturable out)
    {
        out.capture();
        System.out.print(EXPECTED_OUT);
        assertEquals(EXPECTED_OUT, out.getCapturedData());
    }

    @Test
    void testCaptureSystemOutAndErrTogether(@SysOut final Capturable out, @SysErr final Capturable err)
    {

        out.capture();
        System.out.print(EXPECTED_OUT);
        final String expectedErr = "And this to STDERR.";
        err.capture();
        System.err.print(expectedErr);
        assertAll(() -> assertEquals(EXPECTED_OUT, out.getCapturedData()),
                () -> assertEquals(expectedErr, err.getCapturedData()));
    }

    @Test
    void testCaptureSystemErr(@SysErr final Capturable err)
    {
        err.capture();
        System.err.print(EXPECTED_ERR);
        assertEquals(EXPECTED_ERR, err.getCapturedData());
    }
}
