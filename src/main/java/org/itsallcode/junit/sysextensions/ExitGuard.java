package org.itsallcode.junit.sysextensions;

import org.itsallcode.junit.sysextensions.security.ExitGuardSecurityManager;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;

public final class ExitGuard
        implements TestInstancePostProcessor, BeforeTestExecutionCallback, AfterTestExecutionCallback, AfterAllCallback
{
    private static final String PREVIOUS_SECURITY_MANAGER_KEY = "PREV_SECMAN";
    private static final String EXIT_GUARD_SECURITY_MANAGER_KEY = "EXIT_SECMAN";

    @Override
    public void postProcessTestInstance(final Object testInstance, final ExtensionContext context)
    {
        saveCurrentSecurityManager(context);
        installExitGuardSecurityManager(context);
    }

    private void saveCurrentSecurityManager(final ExtensionContext context)
    {
        context.getStore(getNamespace()).put(PREVIOUS_SECURITY_MANAGER_KEY, System.getSecurityManager());
    }

    private void installExitGuardSecurityManager(final ExtensionContext context)
    {
        final SecurityManager previousSecurityManager = getPreviousSecurityManager(context);
        final SecurityManager exitGuardSecurityManager = new ExitGuardSecurityManager(previousSecurityManager);
        System.setSecurityManager(exitGuardSecurityManager);
        context.getStore(getNamespace()).put(EXIT_GUARD_SECURITY_MANAGER_KEY, exitGuardSecurityManager);
    }

    private Namespace getNamespace()
    {
        return Namespace.create(ExitGuard.class);
    }

    private ExitGuardSecurityManager getExitGuardSecurityManager(final ExtensionContext context)
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

    private SecurityManager getPreviousSecurityManager(final ExtensionContext context)
    {
        return (SecurityManager) context.getStore(getNamespace())
                .get(PREVIOUS_SECURITY_MANAGER_KEY);
    }
}