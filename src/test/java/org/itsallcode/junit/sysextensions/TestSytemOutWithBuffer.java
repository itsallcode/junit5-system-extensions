package org.itsallcode.junit.sysextensions;

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
