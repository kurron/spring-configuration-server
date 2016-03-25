package org.kurron.example.core

import java.time.Instant
import org.kurron.example.outbound.TimeService
import org.kurron.feedback.AbstractFeedbackAware
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * An example of a core component.  Remember, components can only interact with
 * other components and outbound gateways.  All the work they do is done in-memory,
 * no touching the network, filesystem or anything else that exists outside the process.
 **/
@Component
class DefaultTimeComponent extends AbstractFeedbackAware implements TimeComponent {

    private TimeService gateway

    @Autowired
    DefaultTimeComponent( TimeService aGateway ) {
        gateway = aGateway
    }

    @Override
    Instant currentTime() {
        // in a real implementation we would interact with multiple services and take
        // the best result but this is only an example
        gateway.checkTheTime()
    }
}
