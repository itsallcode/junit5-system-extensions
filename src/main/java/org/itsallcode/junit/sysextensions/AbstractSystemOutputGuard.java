package org.itsallcode.junit.sysextensions;

import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Executable;
import java.lang.reflect.Parameter;

import org.itsallcode.io.Capturable;
import org.itsallcode.io.CapturingOutputStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.*;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;

/**
 * Base class for JUnit 5 extensions that guard {@link System#out} and
 * {@link System#err} streams.
 */
public abstract class AbstractSystemOutputGuard implements BeforeEachCallback, ParameterResolver, AfterEachCallback
{
    /** The key for the previous output stream. */
    protected static final String PREVIOUS_OUTPUT_STREAM_KEY = "PREV_OSTREAM";
    /** The key for the capturing output stream. */
    protected static final String CAPTURING_OUTPUT_STREAM_KEY = "CAPT_OSTREAM";

    /**
     * Creates a new instance of this class.
     */
    protected AbstractSystemOutputGuard()
    {
        // Default constructor
    }

    @Override
    public void beforeEach(final ExtensionContext context) throws Exception
    {
        saveCurrentSystemStream(context);
        flushSystemStream();
        replaceSystemStreamWithCapturingStream(context);
    }

    private void saveCurrentSystemStream(final ExtensionContext context)
    {
        context.getStore(getNamespace()).put(PREVIOUS_OUTPUT_STREAM_KEY, getSystemStream());
    }

    /**
     * Get the namespace for the extension.
     * 
     * @return the namespace
     */
    protected abstract Namespace getNamespace();

    /**
     * Get the system stream.
     * 
     * @return the system stream
     */
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

    /**
     * Set the system stream to the given stream.
     * 
     * @param systemStream
     *            the stream to set
     */
    protected abstract void setSystemStream(final PrintStream systemStream);

    /**
     * Get the capturing output stream from the context.
     * 
     * @param context
     *            the context
     * @return the capturing output stream
     */
    protected CapturingOutputStream getCapturingOutputStream(final ExtensionContext context)
    {
        return (CapturingOutputStream) context.getStore(getNamespace()).get(CAPTURING_OUTPUT_STREAM_KEY);
    }

    @Override
    public boolean supportsParameter(final ParameterContext parameterContext, final ExtensionContext extensionContext)
            throws ParameterResolutionException
    {
        return isViableMethod(parameterContext) && isParameterCapturingStream(parameterContext)
                && isCompatibleAnnotation(parameterContext);
    }

    /**
     * Check if the method is viable for parameter resolution.
     * 
     * @param parameterContext
     *            the parameter context
     * @return {@code true} if the method is viable
     */
    protected boolean isViableMethod(final ParameterContext parameterContext)
    {
        final Executable method = parameterContext.getDeclaringExecutable();
        return method.isAnnotationPresent(Test.class) || method.isAnnotationPresent(BeforeEach.class);
    }

    /**
     * Check if the parameter is a capturing stream.
     * 
     * @param parameterContext
     *            the parameter context
     * @return {@code true} if the parameter is a capturing stream
     */
    protected boolean isParameterCapturingStream(final ParameterContext parameterContext)
    {
        return (parameterContext.getParameter().getType().equals(Capturable.class));
    }

    private boolean isCompatibleAnnotation(final ParameterContext parameterContext)
    {
        final Parameter parameter = parameterContext.getParameter();
        return (parameter.getAnnotations().length == 0) || parameter.isAnnotationPresent(getParameterAnnotation());

    }

    /**
     * Get the parameter annotation that indicates the parameter is a capturing.
     * 
     * @return the parameter annotation
     */
    protected abstract Class<? extends Annotation> getParameterAnnotation();

    @Override
    public Object resolveParameter(final ParameterContext parameterContext, final ExtensionContext extensionContext)
            throws ParameterResolutionException
    {
        return getCapturingOutputStream(extensionContext);
    }

    @Override
    public void afterEach(final ExtensionContext context) throws Exception
    {
        restorePreviousSystemStream(context);
        closeCapturingOutputStream(context);
    }

    private void restorePreviousSystemStream(final ExtensionContext context)
    {
        setSystemStream(getPreviousStream(context));
    }

    /**
     * Get the previous stream from the context.
     * 
     * @param context
     *            the context
     * @return the previous stream
     */
    protected PrintStream getPreviousStream(final ExtensionContext context)
    {
        return (PrintStream) context.getStore(getNamespace()).get(PREVIOUS_OUTPUT_STREAM_KEY);
    }

    /**
     * Close the capturing output stream.
     * 
     * @param context
     *            the context
     */
    protected void closeCapturingOutputStream(final ExtensionContext context)
    {
        try
        {
            getCapturingOutputStream(context).close();
        }
        catch (final IOException exception)
        {
            throw new UncheckedIOException("Failed to close capturing output stream", exception);
        }
    }
}
