package org.itsallcode.io;

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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestCapturingOuputStream
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
        } finally
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