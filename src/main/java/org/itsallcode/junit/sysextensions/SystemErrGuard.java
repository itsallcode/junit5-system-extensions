package org.itsallcode.junit.sysextensions;

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

import java.io.PrintStream;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.itsallcode.io.Capturable;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;

public class SystemErrGuard extends AbstractSystemOutputGuard
{
    private static final Namespace NAMESPACE = Namespace.create(SystemErrGuard.class);

    /**
     * This annotation can be used on a parameter of type {@link Capturable} to
     * ensure that the placeholder for <code>System.err</code> is injected.
     */
    @Target(ElementType.PARAMETER)
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public @interface SysErr
    {
    }

    @Override
    protected Namespace getNamespace()
    {
        return NAMESPACE;
    }

    @SuppressWarnings("java:S106")
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

    @Override
    protected Class<SysErr> getParameterAnnotation()
    {
        return SysErr.class;
    }
}
