package org.itsallcode.io;

public interface Capturable
{
    /**
     * Activate capturing
     */
    public void capture();

    /**
     * Activate muted capturing, i.e. don't forward output to the underlying
     * output stream. This can be useful to speedup tests.
     */
    public void captureMuted();

    /**
     * Get the data that was captured.
     *
     * @return captured data.
     */
    public String getCapturedData();
}
