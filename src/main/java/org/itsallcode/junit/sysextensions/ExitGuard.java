package org.itsallcode.junit.sysextensions;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.itsallcode.junit.sysextensions.security.ExitGuardSecurityManager;
import org.itsallcode.junit.sysextensions.security.ExitSecurityException;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;

public final class ExitGuard implements TestInstancePostProcessor, ParameterResolver, AfterAllCallback
{
    private static final String PREVISOUS_SECURITY_MANAGER_KEY = "PREV_SECMAN";
    private static final String EXIT_GUARD_SECURITY_MANAGER_KEY = "EXIT_SECMAN";

    public static void assertExitWithCode(final int expectedExitCode, final Runnable runnable)
    {
        try
        {
            runnable.run();
        } catch (final ExitSecurityException e)
        {
            assertEquals("Expected exit status code", expectedExitCode, e.getExitStatus());
            return;
        }
        fail("Lambda did not cause a system exit as expected.");
    }

    @Override
    public void postProcessTestInstance(final Object arg0, final ExtensionContext context) throws Exception
    {
        saveCurrentSecurityManager(context);
        installExitGuardSecurityManager(context);
    }

    private void saveCurrentSecurityManager(final ExtensionContext context)
    {
        context.getStore(getNamespace()).put(PREVISOUS_SECURITY_MANAGER_KEY, System.getSecurityManager());
    }

    private void installExitGuardSecurityManager(final ExtensionContext context)
    {
        final SecurityManager exitGuardSecurityManager = new ExitGuardSecurityManager();
        System.setSecurityManager(exitGuardSecurityManager);
        context.getStore(getNamespace()).put(EXIT_GUARD_SECURITY_MANAGER_KEY, exitGuardSecurityManager);
    }

    private Namespace getNamespace()
    {
        return Namespace.create(ExitGuard.class);
    }

    @Override
    public boolean supportsParameter(final ParameterContext parameterContext, final ExtensionContext context)
            throws ParameterResolutionException
    {
        return true;
    }

    @Override
    public Object resolveParameter(final ParameterContext parameterContext, final ExtensionContext context)
            throws ParameterResolutionException
    {
        return getExitGuardSecurityHandler(context);
    }

    private Object getExitGuardSecurityHandler(final ExtensionContext context)
    {
        return context.getStore(getNamespace()).get(EXIT_GUARD_SECURITY_MANAGER_KEY);
    }

    @Override
    public void afterAll(final ExtensionContext context) throws Exception
    {
//        final SecurityManager previousManager = (SecurityManager) context.getStore(getNamespace())
//                .get(SECURITY_MANAGER_KEY);
//        System.setSecurityManager(previousManager);
    }
}