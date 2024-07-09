package org.itsallcode.io;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * This {@link OutputStream} captures the output written to it and makes it
 * available as a string.
 */
public class CapturingOutputStream extends OutputStream implements Capturable
{
    private OutputStream targetStream;
    private ByteArrayOutputStream internalStream;
    private String captureBuffer;
    private boolean forwardOutputToTarget = true;

    /**
     * Creates a new instance of this class.
     * 
     * @param targetStream
     *            the stream to which the output should be forwarded.
     */
    public CapturingOutputStream(final OutputStream targetStream)
    {
        this.targetStream = Objects.requireNonNull(targetStream, "targetStream");
    }

    @Override
    public void write(final int b) throws IOException
    {
        if (this.internalStream != null)
        {
            this.internalStream.write(b);
        }
        if (this.forwardOutputToTarget && (this.targetStream != null))
        {
            this.targetStream.write(b);
        }
    }

    @Override
    public void write(final byte[] b, final int off, final int len) throws IOException
    {
        if (this.internalStream != null)
        {
            this.internalStream.write(b, off, len);
        }
        if (this.forwardOutputToTarget && (this.targetStream != null))
        {
            this.targetStream.write(b, off, len);
        }
    }

    @Override
    public void flush() throws IOException
    {
        if (this.internalStream != null)
        {
            this.internalStream.flush();
        }
        if (this.targetStream != null)
        {
            this.targetStream.flush();
        }
    }

    @Override
    public synchronized void close() throws IOException
    {
        if (this.internalStream != null)
        {
            this.captureBuffer = this.internalStream.toString(StandardCharsets.UTF_8);
            this.internalStream.close();
        }
        this.internalStream = null;
        this.targetStream = null;
        // closing the targetStream is the responsibility of the class which
        // opened it.
        super.close();
    }

    @Override
    public void capture()
    {
        this.internalStream = new ByteArrayOutputStream();
    }

    @Override
    public void captureMuted()
    {
        this.forwardOutputToTarget = false;
        capture();
    }

    @Override
    public String getCapturedData()
    {
        if (this.internalStream == null)
        {
            return getDataFromCaptureBuffer();
        }
        else
        {
            return this.internalStream.toString(StandardCharsets.UTF_8);
        }
    }

    private String getDataFromCaptureBuffer()
    {
        return (this.captureBuffer == null) ? "" : this.captureBuffer;
    }
}
