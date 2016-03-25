package org.kurron.example.outbound

import java.time.Instant
import org.kurron.stereotype.ServiceStub

/**
 * A "fake" implementation of the time service used in testing.
 **/
@ServiceStub
class StubTimeService implements TimeService {

    @Override
    Instant checkTheTime() {
        Instant.now()
    }
}
