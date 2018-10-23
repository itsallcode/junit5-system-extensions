package org.itsallcode.junit.sysextensions;

import java.io.OutputStream;
import java.io.PrintStream;

import org.itsallcode.io.CapturingOutputStream;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;

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
public class SystemOutGuard extends AbstractSystemOutputGuard

{
    @Override
    protected Namespace getNamespace()
    {
        return Namespace.create(SystemOutGuard.class);
    }

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
}