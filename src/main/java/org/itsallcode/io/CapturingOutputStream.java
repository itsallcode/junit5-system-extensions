package org.itsallcode.io;

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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * This class implements an output stream that can capture the data put into it
 * on demand. It is intended for testing purposes where the tester needs to
 * snoop into an output streams content.
 *
 * <p>
 * When provided with a target output stream, it additionally forwards all data
 * to that stream, working like a "tee" for the streams. The "tee" feature
 * allows the program under test to operate normally.
 * </p>
 *
 * <p>
 * To avoid capturing unwanted data, capturing must be explicitly enabled by
 * calling {@link #capture()}.
 */
public class CapturingOutputStream extends OutputStream
{
    OutputStream targetStream;
    ByteArrayOutputStream internalStream;
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

    /**
     * Activate capturing
     */
    public void capture()
    {
        this.capture = true;
    }

    /**
     * Get the data that was captured.
     *
     * @return captured data.
     */
    public String getCapturedData()
    {
        return this.internalStream.toString();
    }

    /**
     * Reset the captured data buffer and switch capturing off
     */
    public void resetCapturing()
    {
        this.capture = false;
        this.internalStream = new ByteArrayOutputStream();
    }
}
