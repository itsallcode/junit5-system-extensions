package org.itsallcode.io;

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

/**
 * This interface defines a data source which can capture incoming data and make
 * it available for inspection.
 */
public interface Capturable
{
    /**
     * Activate capturing
     */
    public void capture();

    /**
     * Get the data that was captured.
     *
     * @return captured data.
     */
    public String getCapturedData();

    /**
     * Reset the captured data buffer and switch capturing off
     */
    public void resetCapturing();
}
