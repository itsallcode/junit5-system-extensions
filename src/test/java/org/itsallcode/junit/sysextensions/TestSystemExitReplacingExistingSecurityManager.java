
package org.itsallcode.junit.sysextensions;

import static org.junit.jupiter.api.Assertions.*;

import org.itsallcode.junit.sysextensions.security.ExitGuardSecurityManager;
import org.itsallcode.junit.sysextensions.security.ExitTrapException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnJre;
import org.junit.jupiter.api.condition.JRE;

@DisabledOnJre(value = {
        JRE.JAVA_21 }, disabledReason = "The Security Manager is deprecated and will be removed in a future release")
class TestSystemExitReplacingExistingSecurityManager
{
    @Test
    void testFirstSystemExitIntercepted()
    {
        final SecurityManager previousSecurityManager = System.getSecurityManager();
        try
        {
            final SecurityManagerStub securityManagerStub = new SecurityManagerStub();
            final ExitGuardSecurityManager exitGuardSecurityManager = new ExitGuardSecurityManager(securityManagerStub);
            System.setSecurityManager(exitGuardSecurityManager);
            exitGuardSecurityManager.trapExit(true);
            assertAll(() -> assertThrows(ExitTrapException.class, () -> System.exit(1)),
                    () -> assertFalse(securityManagerStub.wasCheckExitCalled(), "Delegate exit called"));
        }
        finally
        {
            System.setSecurityManager(previousSecurityManager);
        }
    }

    @Test
    void testSecurityCheckGetsDelegated()
    {
        final SecurityManager previousSecurityManager = System.getSecurityManager();
        try
        {
            final SecurityManagerStub securityManagerStub = new SecurityManagerStub();
            final ExitGuardSecurityManager exitGuardSecurityManager = new ExitGuardSecurityManager(securityManagerStub);
            System.setSecurityManager(exitGuardSecurityManager);
            System.getProperty("any-property");
            assertTrue(securityManagerStub.wasCheckPropertyAccessCalled());
        }
        finally
        {
            System.setSecurityManager(previousSecurityManager);
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
