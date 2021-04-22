package org.itsallcode.junit.sysextensions;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.itsallcode.junit.sysextensions.security.ExitGuardSecurityManager;
import org.itsallcode.junit.sysextensions.security.ExitTrapException;
import org.junit.jupiter.api.Test;

class TestSystemExitReplacingExistingSecurityManager
{
    @Test
    void testFirstSystemExitIntercepted()
    {
        final SecurityManager previousSecuritymanager = System.getSecurityManager();
        try
        {
            final SecurityManagerStub securityManagerStub = new SecurityManagerStub();
            final ExitGuardSecurityManager exitGuardsecurityManager = new ExitGuardSecurityManager(securityManagerStub);
            System.setSecurityManager(exitGuardsecurityManager);
            exitGuardsecurityManager.trapExit(true);
            assertAll(() -> assertThrows(ExitTrapException.class, () -> System.exit(1)),
                    () -> assertFalse(securityManagerStub.wasCheckExitCalled(), "Delegate exit called"));
        }
        finally
        {
            System.setSecurityManager(previousSecuritymanager);
        }
    }

    @Test
    void testSecurityCheckGetsDelegated()
    {
        final SecurityManager previousSecuritymanager = System.getSecurityManager();
        try
        {
            final SecurityManagerStub securityManagerStub = new SecurityManagerStub();
            final ExitGuardSecurityManager exitGuardsecurityManager = new ExitGuardSecurityManager(securityManagerStub);
            System.setSecurityManager(exitGuardsecurityManager);
            System.getProperty("any-property");
            assertTrue(securityManagerStub.wasCheckPropertyAccessCalled());
        }
        finally
        {
            System.setSecurityManager(previousSecuritymanager);
        }
    }

    private static class SecurityManagerStub extends SecurityManager
    {
        private boolean checkExitCalled = false;
        private boolean checkPropertyAccessCalled = false;

        @Override
        public void checkExit(final int status)
        {
            this.checkExitCalled = true;
            super.checkExit(status);
        }

        @Override
        public void checkPropertyAccess(final String key)
        {
            this.checkPropertyAccessCalled = true;
        }

        public boolean wasCheckExitCalled()
        {
            return this.checkExitCalled;
        }

        public boolean wasCheckPropertyAccessCalled()
        {
            return this.checkPropertyAccessCalled;
        }
    }
}