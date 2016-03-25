package org.kurron.example

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.boot.test.WebIntegrationTest
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

/**
 * Just proves that the application context can properly start.
 */
@WebIntegrationTest( randomPort = true )
@ContextConfiguration( classes = Application, loader = SpringApplicationContextLoader )
class ApplicationIntegrationTest extends Specification {

    @Autowired
    ApplicationProperties configuration

    def 'verify context loads'() {
        expect: 'the configuration bean was injected'
        'default' == configuration.foo
    }
}
