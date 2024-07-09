package org.itsallcode.io;

/**
 * Interface for classes that can capture output.
 */
public interface Capturable
{
    /**
     * Activate capturing
     */
    void capture();

    /**
     * Activate muted capturing, i.e. don't forward output to the underlying
     * output stream. This can be useful to speedup tests.
     */
    void captureMuted();

    /**
     * Get the data that was captured.
     *
     * @return captured data.
     */
    String getCapturedData();
}
