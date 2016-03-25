package org.kurron.example.outbound

import java.time.Instant
import org.junit.experimental.categories.Category
import org.kurron.categories.OutboundIntegrationTest
import org.kurron.example.Application
import org.kurron.traits.GenerationAbility
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.IntegrationTest
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

/**
 * An integration-level test of the RemoteTimeGateway object.
 **/
@Category( OutboundIntegrationTest )
@IntegrationTest
@ContextConfiguration( classes = Application, loader = SpringApplicationContextLoader )
class SampleGatewayIntegrationTest extends Specification implements GenerationAbility {

    @Autowired
    @Qualifier( 'production' )
    TimeService sut

    def 'exercise checkTheTime happy path'() {
        given: 'a proper testing environment'
        assert sut

        when: 'we call checkTheTime'
        def results = sut.checkTheTime()

        then: 'we get a proper response'
        Instant.now().epochSecond == results.epochSecond
    }
}
