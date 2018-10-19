import static org.junit.jupiter.api.Assertions.fail;

import org.itsallcode.junit.sysextensions.ExitGuard;
import org.itsallcode.junit.sysextensions.security.ExitGuardSecurityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(ExitGuard.class)
class TestSystemExit
{
    @Test
    void testSystemExit1(final ExitGuardSecurityManager manager)
    {
        manager.trapExit(true);
        ExitGuard.assertExitWithCode(1, () -> System.exit(1));
    }

    @Test
    void testSystemExit0(final ExitGuardSecurityManager manager)
    {
        manager.trapExit(true);
        ExitGuard.assertExitWithCode(0, () -> System.exit(0));
    }

    @Test
    void testSystemMissingExitRaisesAssertException(final ExitGuardSecurityManager manager)
    {
        manager.trapExit(true);
        try
        {
            ExitGuard.assertExitWithCode(0, () -> {
                // intentionally empty
            });
        } catch (final AssertionError assertionError)
        {
            return;
        }
        fail("Code sequence where exit was expected did not exit, no assertion error was raised");
    }
}