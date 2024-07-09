package org.itsallcode.junit.sysextensions;

import org.itsallcode.junit.sysextensions.security.ExitGuardSecurityManager;
import org.junit.jupiter.api.extension.*;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;

/**
 * JUnit 5 extension that guards calls to {@link System#exit(int)}.
 * 
 * @deprecated Class {@link SecurityManager} used by ExitGuard is deprecated and
 *             does not work with JRE 21 and later.
 */
@Deprecated(since = "1.2.1", forRemoval = true)
public final class ExitGuard
        implements TestInstancePostProcessor, BeforeTestExecutionCallback, AfterTestExecutionCallback, AfterAllCallback
{
    private static final String PREVIOUS_SECURITY_MANAGER_KEY = "PREV_SECMAN";
    private static final String EXIT_GUARD_SECURITY_MANAGER_KEY = "EXIT_SECMAN";

    /**
     * Creates a new instance of this class.
     */
    public ExitGuard()
    {
        // Default constructor
    }

    @Override
    public void postProcessTestInstance(final Object testInstance, final ExtensionContext context)
    {
        saveCurrentSecurityManager(context);
        installExitGuardSecurityManager(context);
    }

    private static void saveCurrentSecurityManager(final ExtensionContext context)
    {
        context.getStore(getNamespace()).put(PREVIOUS_SECURITY_MANAGER_KEY, System.getSecurityManager());
    }

    private static void installExitGuardSecurityManager(final ExtensionContext context)
    {
        final SecurityManager previousSecurityManager = getPreviousSecurityManager(context);
        final SecurityManager exitGuardSecurityManager = new ExitGuardSecurityManager(previousSecurityManager);
        System.setSecurityManager(exitGuardSecurityManager);
        context.getStore(getNamespace()).put(EXIT_GUARD_SECURITY_MANAGER_KEY, exitGuardSecurityManager);
    }

    private static Namespace getNamespace()
    {
        return Namespace.create(ExitGuard.class);
    }

    private static ExitGuardSecurityManager getExitGuardSecurityManager(final ExtensionContext context)
    {
        return (ExitGuardSecurityManager) context.getStore(getNamespace()).get(EXIT_GUARD_SECURITY_MANAGER_KEY);
    }

    @Override
    public void beforeTestExecution(final ExtensionContext context) throws Exception
    {
        getExitGuardSecurityManager(context).trapExit(true);
    }

    @Override
    public void afterTestExecution(final ExtensionContext context) throws Exception
    {
        getExitGuardSecurityManager(context).trapExit(false);
    }

    @Override
    public void afterAll(final ExtensionContext context) throws Exception
    {
        final SecurityManager previousManager = getPreviousSecurityManager(context);
        System.setSecurityManager(previousManager);
    }

    private static SecurityManager getPreviousSecurityManager(final ExtensionContext context)
    {
        return (SecurityManager) context.getStore(getNamespace())
                .get(PREVIOUS_SECURITY_MANAGER_KEY);
    }
}
