package org.kurron.example.core

import java.time.Instant
import org.junit.experimental.categories.Category
import org.kurron.categories.UnitTest
import org.kurron.example.outbound.TimeService
import org.kurron.traits.GenerationAbility
import spock.lang.Specification

/**
 * A unit-level test of the DefaultComponent object.
 **/
@Category( UnitTest )
class DefaultTimeComponentUnitTest extends Specification implements GenerationAbility {

    def 'exercise currentTime happy path'() {
        given: 'a subject under test'
        def stub = Stub( TimeService )
        stub.checkTheTime() >> Instant.now()

        def sut = new DefaultTimeComponent( stub )

        when: 'we call currentTime'
        def results = sut.currentTime()

        then: 'we get expected results'
        Instant.now().epochSecond == results.epochSecond
    }
}
