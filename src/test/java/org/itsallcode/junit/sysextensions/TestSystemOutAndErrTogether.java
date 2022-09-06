package org.itsallcode.junit.sysextensions;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.itsallcode.io.Capturable;
import org.itsallcode.junit.sysextensions.SystemErrGuard.SysErr;
import org.itsallcode.junit.sysextensions.SystemOutGuard.SysOut;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(SystemOutGuard.class)
@ExtendWith(SystemErrGuard.class)
class TestSystemOutAndErrTogether
{
    final static String EXPECTED_OUT = "This goes to STDOUT.";
    final static String EXPECTED_ERR = "This goes to STDERR.";

    @BeforeEach
    void BeforeEach()
    {
        System.out.print("Don't capture this");
        System.err.print("Or this");
    }

    @Test
    void testCaptureSystemOut(@SysOut final Capturable out)
    {
        out.capture();
        System.out.print(EXPECTED_OUT);
        assertEquals(EXPECTED_OUT, out.getCapturedData());
    }

    @Test
    void testCaptureSystemOutAndErrTogether(@SysOut final Capturable out, @SysErr final Capturable err)
    {

        out.capture();
        System.out.print(EXPECTED_OUT);
        final String expectedErr = "And this to STDERR.";
        err.capture();
        System.err.print(expectedErr);
        assertAll(() -> assertEquals(EXPECTED_OUT, out.getCapturedData()),
                () -> assertEquals(expectedErr, err.getCapturedData()));
    }

    @Test
    void testCaptureSystemErr(@SysErr final Capturable err)
    {
        err.capture();
        System.err.print(EXPECTED_ERR);
        assertEquals(EXPECTED_ERR, err.getCapturedData());
    }
}
