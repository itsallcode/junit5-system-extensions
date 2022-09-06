package org.itsallcode.junit.sysextensions;

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
        assertEquals("", stream.getCapturedData());
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
