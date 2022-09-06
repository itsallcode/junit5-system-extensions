package org.itsallcode.junit.sysextensions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.itsallcode.io.Capturable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(SystemOutGuard.class)
class TestSystemOutWithBeforeEach
{
    @BeforeEach
    void beforeEach(final Capturable stream)
    {
        final String ignored = "Ignore this text here." + System.lineSeparator();
        System.out.print(ignored);
        stream.capture();
    }

    @Test
    void testCaptureAfterEachA(final Capturable stream)
    {
        final String expected = "This text must be captured." + System.lineSeparator();
        System.out.print(expected);
        assertEquals(stream.getCapturedData(), expected);
    }

    @Test
    void testCaptureAfterEachB(final Capturable stream)
    {
        final String expected = "Again: this must be captured." + System.lineSeparator();
        System.out.print(expected);
        assertEquals(stream.getCapturedData(), expected);
    }
}
