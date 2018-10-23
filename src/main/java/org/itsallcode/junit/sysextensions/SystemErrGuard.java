package org.itsallcode.junit.sysextensions;

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

import java.io.OutputStream;
import java.io.PrintStream;

import org.itsallcode.io.Capturable;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;

/**
 * This class implements a JUnit extension that lets you capture data written to
 * {@link System#err}.
 *
 * <p>
 * For this it provides the user with a special kind of {@link OutputStream},
 * represented by the {@link Capturable} interface. If you add this interface as
 * a parameter to a method annotated with either <code>@BeforeTest</code> or
 * <code>@Test</code>, the {@link SystemOutGuard} injects an suitable
 * implementation.
 * </p>
 *
 * <p>
 * For the user's convenience capturing is automatically reset before each call
 * to the method annotated with <code>@Before</code>.
 */
public class SystemErrGuard extends AbstractSystemOutputGuard
{
    private static final Namespace NAMESPACE = Namespace.create(SystemErrGuard.class);

    @Override
    protected Namespace getNamespace()
    {
        return NAMESPACE;
    }

    @Override
    protected PrintStream getSystemStream()
    {
        return System.err;
    }

    @Override
    protected void setSystemStream(final PrintStream systemStream)
    {
        System.setErr(systemStream);
    }
}