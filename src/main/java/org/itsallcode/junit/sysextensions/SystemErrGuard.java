package org.itsallcode.junit.sysextensions;

import java.io.PrintStream;
import java.lang.annotation.*;

import org.itsallcode.io.Capturable;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;

/**
 * JUnit 5 extension that guards {@link System#err} stream.
 */
public class SystemErrGuard extends AbstractSystemOutputGuard
{
    private static final Namespace NAMESPACE = Namespace.create(SystemErrGuard.class);

    /**
     * Creates a new instance of this class.
     */
    public SystemErrGuard()
    {
        // Default constructor
    }
    
    /**
     * This annotation can be used on a parameter of type {@link Capturable} to
     * ensure that the placeholder for <code>{@link System#out}</code> is
     * injected.
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

    @SuppressWarnings("java:S106") // System.err used intentionally
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
