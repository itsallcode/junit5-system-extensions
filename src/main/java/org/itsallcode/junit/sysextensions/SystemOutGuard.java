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

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.Executable;

import org.itsallcode.io.CapturingOutputStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

/**
 * This class implements a JUnit extension that lets you capture data written to
 * {@link System#out}. in
 *
 * <p>
 * For this it provides the user with a special kind of {@link OutputStream},
 * the {@link CapturingOutputStream}. If you add that stream as a parameter to a
 * method annotated with either <code>@BeforeTest</code> or <code>@Test</code> ,
 * the {@link SystemOutGuard} injects an instance of the
 * {@link CapturingOutputStream}.
 * </p>
 *
 * <p>
 * For the users convenience capturing is automatically reset before each call
 * to the method annotated with <code>@Before</code>.
 */
public class SystemOutGuard implements BeforeAllCallback, BeforeEachCallback, ParameterResolver, AfterAllCallback
{
    private static final String PREVIOUS_OUTPUT_STREAM_KEY = "PREV_OSTREAM";
    private static final String CAPTURING_OUTPUT_STREAM_KEY = "CAPT_OSTREAM";

    @Override
    public void beforeAll(final ExtensionContext context) throws Exception
    {
        saveCurrentSystemOut(context);
        flushSystemOut();
        replaceSystemOutWithCapturingStream(context);

    }

    private void saveCurrentSystemOut(final ExtensionContext context)
    {
        context.getStore(getNamespace()).put(PREVIOUS_OUTPUT_STREAM_KEY, System.out);
    }

    private void flushSystemOut()
    {
        System.out.flush();
    }

    private void replaceSystemOutWithCapturingStream(final ExtensionContext context)
    {
        final CapturingOutputStream capturingStream = new CapturingOutputStream(System.out);
        context.getStore(getNamespace()).put(CAPTURING_OUTPUT_STREAM_KEY, capturingStream);
        System.setOut(new PrintStream(getCapturingOutputStream(context)));
    }

    private CapturingOutputStream getCapturingOutputStream(final ExtensionContext context)
    {
        return (CapturingOutputStream) context.getStore(getNamespace()).get(CAPTURING_OUTPUT_STREAM_KEY);
    }

    private Namespace getNamespace()
    {
        return Namespace.create(SystemOutGuard.class);
    }

    @Override
    public void beforeEach(final ExtensionContext context) throws Exception
    {
        getCapturingOutputStream(context).resetCapturing();
    }

    @Override
    public boolean supportsParameter(final ParameterContext parameterContext, final ExtensionContext extensionContext)
            throws ParameterResolutionException
    {
        return isViableMethod(parameterContext) && isParameterCapturingStream(parameterContext);
    }

    private boolean isViableMethod(final ParameterContext parameterContext)
    {
        final Executable method = parameterContext.getDeclaringExecutable();
        return method.isAnnotationPresent(Test.class) || method.isAnnotationPresent(BeforeEach.class);
    }

    private boolean isParameterCapturingStream(final ParameterContext parameterContext)
    {
        return (parameterContext.getParameter().getType().equals(CapturingOutputStream.class));
    }

    @Override
    public Object resolveParameter(final ParameterContext parameterContext, final ExtensionContext extensionContext)
            throws ParameterResolutionException
    {
        return getCapturingOutputStream(extensionContext);
    }

    @Override
    public void afterAll(final ExtensionContext context) throws Exception
    {
        restorePreviousSystemOut(context);
        closeCapturingOutputStream(context);
    }

    private void restorePreviousSystemOut(final ExtensionContext context)
    {
        System.setOut((PrintStream) context.getStore(getNamespace()).get(PREVIOUS_OUTPUT_STREAM_KEY));
    }

    private void closeCapturingOutputStream(final ExtensionContext context) throws IOException
    {
        getCapturingOutputStream(context).close();
    }
}
