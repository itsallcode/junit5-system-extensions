package org.itsallcode.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.io.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestCapturingOutputStream
{
    private static final byte[] CONTENT = "content".getBytes();
    private OutputStream delegate;
    private CapturingOutputStream streamWithMockDelegate;

    @BeforeEach
    void setup()
    {
        this.delegate = mock(OutputStream.class);
        this.streamWithMockDelegate = new CapturingOutputStream(this.delegate);
    }

    @Test
    void testGetCapturedDataAfterClose() throws IOException
    {
        final CapturingOutputStream stream = new CapturingOutputStream(System.out);
        stream.capture();
        final String expected = "This must be available even after the stream is closed.";
        stream.write(expected.getBytes());
        stream.close();
        assertEquals(expected, stream.getCapturedData());
    }

    @Test
    void testGetCapturedDataAfterMutedCapturing() throws IOException
    {
        final CapturingOutputStream stream = new CapturingOutputStream(System.out);
        stream.captureMuted();
        final String expected = "This must be available even after the stream is closed.";
        stream.write(expected.getBytes());
        stream.close();
        assertEquals(expected, stream.getCapturedData());
    }

    @Test
    void testWithBufferedOutputStreamOnTopOfSystemOut() throws IOException
    {
        final PrintStream previousOut = System.out;
        try
        {
            final CapturingOutputStream stream = new CapturingOutputStream(previousOut);
            System.setOut(new PrintStream(stream));
            stream.capture();
            final String expected = "Expected";
            try (final BufferedOutputStream bufferedStream = new BufferedOutputStream(System.out))
            {
                bufferedStream.write(expected.getBytes());
                bufferedStream.flush();
            }
            assertEquals(expected, stream.getCapturedData());
        }
        finally
        {
            System.setOut(previousOut);
        }
    }

    @Test
    void testOutputForwardedWhenCapturing() throws IOException
    {
        this.streamWithMockDelegate.capture();
        this.streamWithMockDelegate.write(CONTENT);
        this.streamWithMockDelegate.close();
        verify(this.delegate).write(CONTENT, 0, CONTENT.length);
    }

    @Test
    void testOutputForwardedWhenNotCapturing() throws IOException
    {
        this.streamWithMockDelegate.write(CONTENT);
        this.streamWithMockDelegate.close();
        verify(this.delegate).write(CONTENT, 0, CONTENT.length);
    }

    @Test
    void testOutputNotForwardedWhenCapturingInMuteMode() throws IOException
    {
        this.streamWithMockDelegate.captureMuted();
        this.streamWithMockDelegate.write(CONTENT);
        this.streamWithMockDelegate.close();
        verifyNoInteractions(this.delegate);
    }
}
