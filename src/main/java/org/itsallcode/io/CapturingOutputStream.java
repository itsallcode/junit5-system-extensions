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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class CapturingOutputStream extends OutputStream implements Capturable
{
    private OutputStream targetStream;
    private ByteArrayOutputStream internalStream;
    private boolean capture;

    public CapturingOutputStream(final OutputStream targetStream)
    {
        this.targetStream = targetStream;
        resetCapturing();
    }

    @Override
    public void write(final int b) throws IOException
    {
        if (this.capture)
        {
            this.internalStream.write(b);
        }
        if (this.targetStream != null)
        {
            this.targetStream.write(b);
        }
    }

    @Override
    public void write(final byte[] b, final int off, final int len) throws IOException
    {
        if (this.capture)
        {
            this.internalStream.write(b, off, len);
        }
        if (this.targetStream != null)
        {
            this.targetStream.write(b, off, len);
        }
    }

    @Override
    public void flush() throws IOException
    {
        if (this.capture)
        {
            this.internalStream.flush();
        }
        if (this.targetStream != null)
        {
            this.targetStream.flush();
        }
    }

    @Override
    public void close() throws IOException
    {
        this.internalStream.close();
        this.internalStream = null;
        this.targetStream = null;
        // closing the targetStream is the responsibility of the class which opened it.
        super.close();
    }

    @Override
    public void capture()
    {
        this.capture = true;
    }

    @Override
    public String getCapturedData()
    {
        return this.internalStream.toString();
    }

    @Override
    public void resetCapturing()
    {
        this.capture = false;
        this.internalStream = new ByteArrayOutputStream();
    }
}