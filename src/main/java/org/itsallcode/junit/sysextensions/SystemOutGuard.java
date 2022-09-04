package org.itsallcode.junit.sysextensions;

import java.io.PrintStream;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.itsallcode.io.Capturable;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;

public class SystemOutGuard extends AbstractSystemOutputGuard
{
    private static final Namespace NAMESPACE = Namespace.create(SystemOutGuard.class);

    /**
     * This annotation can be used on a parameter of type {@link Capturable} to
     * ensure that the placeholder for <code>System.out</code> is injected.
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
