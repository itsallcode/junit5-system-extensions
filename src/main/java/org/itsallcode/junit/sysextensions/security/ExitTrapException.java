package org.itsallcode.junit.sysextensions.security;

/**
 * This exception is thrown by {@link ExitGuardSecurityManager#checkExit(int)}
 * when a call to {@link System#exit(int)} is intercepted.
 */
public class ExitTrapException extends SecurityException
{
    @SuppressWarnings("java:S4926") // serialVersionUID used intentionally
    private static final long serialVersionUID = 3483205912039194022L;

    /**
     * Exit status code.
     * 
     * @serial
     */
    private final int status;

    /**
     * Constructs a new {@link ExitTrapException} with the given message and
     * exit.
     * 
     * @param message
     *            exit message
     * @param status
     *            exit status code
     */
    public ExitTrapException(final String message, final int status)
    {
        super(message);
        this.status = status;
    }

    /**
     * Returns the exit status code.
     * 
     * @return the exit status code
     */
    public int getExitStatus()
    {
        return this.status;
    }
}
