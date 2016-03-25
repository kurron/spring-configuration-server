package org.kurron.example.core

import java.time.Instant

/**
 * An example core component.
 **/
interface TimeComponent {

    /**
     * This routine will obtain the current time from multiple authorities and return
     * only the most accurate result.
     * @return the most accurate result available.
     */
    Instant currentTime()
}
