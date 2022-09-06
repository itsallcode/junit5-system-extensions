package org.itsallcode.junit.sysextensions;

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
