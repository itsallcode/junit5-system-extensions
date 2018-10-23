package org.itsallcode.junit.sysextensions;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.itsallcode.io.Capturable;
import org.itsallcode.junit.sysextensions.SystemErrGuard.SysErr;
import org.itsallcode.junit.sysextensions.SystemOutGuard.SysOut;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(SystemOutGuard.class)
@ExtendWith(SystemErrGuard.class)
class TestSystemOutAndErrTogether
{
    @Test
    void testCaptureSystemOutAndErrTogether(@SysOut final Capturable out, @SysErr final Capturable err)
    {
        final String expectedOut = "This goes to STDOUT.";
        out.capture();
        System.out.print(expectedOut);
        final String expectedErr = "And this to STDERR.";
        err.capture();
        System.err.print(expectedErr);
        assertAll(() -> assertEquals(out.getCapturedData(), expectedOut),
                () -> assertEquals(err.getCapturedData(), expectedErr));
    }
}