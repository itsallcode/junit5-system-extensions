package org.itsallcode.junit.sysextensions;

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
