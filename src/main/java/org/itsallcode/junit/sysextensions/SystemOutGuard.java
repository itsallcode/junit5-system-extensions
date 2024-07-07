package org.itsallcode.junit.sysextensions;

import java.io.PrintStream;
import java.lang.annotation.*;

import org.itsallcode.io.Capturable;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;

/**
 * JUnit 5 extension that guards {@link System#out} stream.
 */
public class SystemOutGuard extends AbstractSystemOutputGuard
{
    private static final Namespace NAMESPACE = Namespace.create(SystemOutGuard.class);

    /**
     * Creates a new instance of this class.
     */
    public SystemOutGuard()
    {
        // Default constructor
    }

    /**
     * This annotation can be used on a parameter of type {@link Capturable} to
     * ensure that the placeholder for {@link System#out} is injected.
     */
    @Target(ElementType.PARAMETER)
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public @interface SysOut
    {
    }

    @Override
    protected Namespace getNamespace()
    {
        return NAMESPACE;
    }

    @SuppressWarnings("java:S106") // System.out used intentionally
    @Override
    protected PrintStream getSystemStream()
    {
        return System.out;
    }

    @Override
    protected void setSystemStream(final PrintStream systemStream)
    {
        System.setOut(systemStream);
    }

    @Override
    protected Class<SysOut> getParameterAnnotation()
    {
        return SysOut.class;
    }
}
