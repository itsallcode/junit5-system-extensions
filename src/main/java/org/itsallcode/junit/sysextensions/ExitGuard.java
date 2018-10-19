package org.itsallcode.junit.sysextensions;

/*-
 * #%L
 * JUnit5 System Extensions
 * %%
 * Copyright (C) 2016 - 2018 itsallcode.org
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

import org.itsallcode.junit.sysextensions.security.ExitGuardSecurityManager;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;

/**
 * This class implements a set of extension points for JUnit5 tests in order to
 * enable trapping system exit calls.
 *
 * <p>
 * At the time of the instantiation of a test class which is extended with this
 * class, {@link ExitGuard} installs an {@link ExitGuardSecurityManager} as the
 * systems security manager which can trap a {@link System#exit(int)} during a
 * security check.
 * </p>
 * <p>
 * Note that this extension therefore needs to replace any existing security
 * managers. While it reinstalls them after all tests are run, this can lead to
 * unexpected behavior during all tests in the annotated class when a security
 * manager was already present.
 * </p>
 * <p>
 * Before each test the trap is activated and it is deactivated after the test.
 * This is done to avoid trapping exits that are outside of the tests.
 * </p>
 */
public final class ExitGuard
        implements TestInstancePostProcessor, BeforeTestExecutionCallback, AfterTestExecutionCallback, AfterAllCallback
{
    private static final String PREVISOUS_SECURITY_MANAGER_KEY = "PREV_SECMAN";
    private static final String EXIT_GUARD_SECURITY_MANAGER_KEY = "EXIT_SECMAN";

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
        final SecurityManager previousManager = (SecurityManager) context.getStore(getNamespace())
                .get(PREVISOUS_SECURITY_MANAGER_KEY);
        System.setSecurityManager(previousManager);
    }
}
