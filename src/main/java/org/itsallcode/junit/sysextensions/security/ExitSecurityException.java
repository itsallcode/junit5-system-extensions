package org.itsallcode.junit.sysextensions.security;

/**
 * This class is a descendant of {@link SecurityException} to enable catching specifically an system exit.
 * That allows us to intercept the <code>exit<code> call an check the status.
 */
public class ExitSecurityException extends SecurityException {
	private static final long serialVersionUID = 3483205912039194022L;
	private int status;

	public ExitSecurityException(String message, int status) {
		super(message);
		this.status = status;
	}
	
	public int getExitStatus() {
		return status;
	}
}