package org.itsallcode.io;

/*-
 * #%L
 * JUnit5 System Extensions
 * %%
 * Copyright (C) 2018 itsallcode.org
 * %%
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the Eclipse
 * Public License, v. 2.0 are satisfied: GNU General Public License, version 2
 * with the GNU Classpath Exception which is
 * available at https://www.gnu.org/software/classpath/license.html.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 * #L%
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;

public class CapturingOutputStream extends OutputStream implements Capturable
{
    private OutputStream targetStream = null;
    private ByteArrayOutputStream internalStream = null;
    private String captureBuffer = null;
    private boolean forwardOutputToTarget = true;

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
            this.captureBuffer = this.internalStream.toString();
            this.internalStream.close();
        }
        this.internalStream = null;
        this.targetStream = null;
        // closing the targetStream is the responsibility of the class which opened it.
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
        } else
        {
            return this.internalStream.toString();
        }
    }

    private String getDataFromCaptureBuffer()
    {
        return (this.captureBuffer == null) ? "" : this.captureBuffer;
    }
}
