package org.kurron.example.outbound

import java.time.Instant

/**
 * An example outbound gateway service interface.
 **/
interface TimeService {

    /**
     * Obtains the current time from an official source.
     * @return the current time, in ISO 8601 format.
     */
    Instant checkTheTime()
}
