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

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.itsallcode.io.Capturable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(SystemErrGuard.class)
class TestSystemErrWithBeforeEach
{
    @BeforeEach
    void beforeEach(final Capturable stream)
    {
        final String ignored = "Ignore this text here." + System.lineSeparator();
        System.err.print(ignored);
        stream.capture();
    }

    @Test
    void testCaptureAfterEachA(final Capturable stream)
    {
        final String expected = "This text must be captured." + System.lineSeparator();
        System.err.print(expected);
        assertEquals(stream.getCapturedData(), expected);
    }

    @Test
    void testCaptureAfterEachB(final Capturable stream)
    {
        final String expected = "Again: this must be captured." + System.lineSeparator();
        System.err.print(expected);
        assertEquals(stream.getCapturedData(), expected);
    }
}