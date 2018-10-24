package org.itsallcode.junit.sysextensions;

/*-
 * #%L
 * JUnit5 System Extensions
 * %%
 * Copyright (C) 2016 - 2018 itsallcode.org
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.itsallcode.io.Capturable;
import org.itsallcode.junit.sysextensions.SystemErrGuard.SysErr;
import org.itsallcode.junit.sysextensions.SystemOutGuard.SysOut;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(SystemOutGuard.class)
@ExtendWith(SystemErrGuard.class)
class TestSystemOutAndErrTogether
{
    @Test
    void testCaptureSystemOutAndErrTogether(@SysOut final Capturable out, @SysErr final Capturable err)
    {
        final String expectedOut = "This goes to STDOUT.";
        out.capture();
        System.out.print(expectedOut);
        final String expectedErr = "And this to STDERR.";
        err.capture();
        System.err.print(expectedErr);
        assertAll(() -> assertEquals(out.getCapturedData(), expectedOut),
                () -> assertEquals(err.getCapturedData(), expectedErr));
    }
}