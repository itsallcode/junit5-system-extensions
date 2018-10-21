package org.itsallcode.junit.sysextensions.security;

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

/**
 * This class is a descendant of {@link SecurityException} to enable catching specifically an system exit.
 * That allows us to intercept the <code>exit<code> call an check the status.
 */
public class ExitTrapException extends SecurityException {
	private static final long serialVersionUID = 3483205912039194022L;
	private int status;

	public ExitTrapException(String message, int status) {
		super(message);
		this.status = status;
	}
	
	public int getExitStatus() {
		return status;
	}
}
