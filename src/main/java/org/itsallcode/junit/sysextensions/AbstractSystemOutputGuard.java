package org.itsallcode.junit.sysextensions;

import java.io.IOException;
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

public abstract class AbstractSystemOutputGuard
        implements BeforeAllCallback, BeforeEachCallback, ParameterResolver, AfterAllCallback
{
    protected static final String PREVIOUS_OUTPUT_STREAM_KEY = "PREV_OSTREAM";
    protected static final String CAPTURING_OUTPUT_STREAM_KEY = "CAPT_OSTREAM";

    @Override
    public void beforeAll(final ExtensionContext context) throws Exception
    {
        saveCurrentSystemStream(context);
        flushSystemStream();
        replaceSystemStreamWithCapturingStream(context);
    }

    private void saveCurrentSystemStream(final ExtensionContext context)
    {
        context.getStore(getNamespace()).put(PREVIOUS_OUTPUT_STREAM_KEY, getSystemStream());
    }

    protected abstract Namespace getNamespace();

    protected abstract PrintStream getSystemStream();

    private void flushSystemStream()
    {
        getSystemStream().flush();
    }

    private void replaceSystemStreamWithCapturingStream(final ExtensionContext context)
    {
        final CapturingOutputStream capturingStream = new CapturingOutputStream(getSystemStream());
        context.getStore(getNamespace()).put(CAPTURING_OUTPUT_STREAM_KEY, capturingStream);
        final PrintStream printStream = new PrintStream(getCapturingOutputStream(context));
        setSystemStream(printStream);
    }

    protected abstract void setSystemStream(final PrintStream systemStream);

    @Override
    public void beforeEach(final ExtensionContext context) throws Exception
    {
        getCapturingOutputStream(context).resetCapturing();
    }

    protected CapturingOutputStream getCapturingOutputStream(final ExtensionContext context)
    {
        return (CapturingOutputStream) context.getStore(getNamespace()).get(CAPTURING_OUTPUT_STREAM_KEY);
    }

    @Override
    public boolean supportsParameter(final ParameterContext parameterContext, final ExtensionContext extensionContext)
            throws ParameterResolutionException
    {
        return isViableMethod(parameterContext) && isParameterCapturingStream(parameterContext);
    }

    protected boolean isViableMethod(final ParameterContext parameterContext)
    {
        final Executable method = parameterContext.getDeclaringExecutable();
        return method.isAnnotationPresent(Test.class) || method.isAnnotationPresent(BeforeEach.class);
    }

    protected boolean isParameterCapturingStream(final ParameterContext parameterContext)
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
        restorePreviousSystemStream(context);
        closeCapturingOutputStream(context);
    }

    private void restorePreviousSystemStream(final ExtensionContext context)
    {
        setSystemStream(getPreviousStream(context));
    }

    protected PrintStream getPreviousStream(final ExtensionContext context)
    {
        return (PrintStream) context.getStore(getNamespace()).get(PREVIOUS_OUTPUT_STREAM_KEY);
    }

    protected void closeCapturingOutputStream(final ExtensionContext context) throws IOException
    {
        getCapturingOutputStream(context).close();
    }
}