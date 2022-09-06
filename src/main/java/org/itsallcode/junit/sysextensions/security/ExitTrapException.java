package org.itsallcode.junit.sysextensions.security;

public class ExitTrapException extends SecurityException
{
    private static final long serialVersionUID = 3483205912039194022L;
    /** @serial */
    private final int status;

    public ExitTrapException(final String message, final int status)
    {
        super(message);
        this.status = status;
    }

    public int getExitStatus()
    {
        return this.status;
    }
}
