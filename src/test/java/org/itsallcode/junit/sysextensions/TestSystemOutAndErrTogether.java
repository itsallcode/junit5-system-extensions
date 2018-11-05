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
        assertEquals(out.getCapturedData(), EXPECTED_OUT);
    }

    @Test
    void testCaptureSystemOutAndErrTogether(@SysOut final Capturable out, @SysErr final Capturable err)
    {

        out.capture();
        System.out.print(EXPECTED_OUT);
        final String expectedErr = "And this to STDERR.";
        err.capture();
        System.err.print(expectedErr);
        assertAll(() -> assertEquals(out.getCapturedData(), EXPECTED_OUT),
                () -> assertEquals(err.getCapturedData(), expectedErr));
    }

    @Test
    void testCaptureSystemErr(@SysErr final Capturable err)
    {
        err.capture();
        System.err.print(EXPECTED_ERR);
        assertEquals(err.getCapturedData(), EXPECTED_ERR);
    }
}