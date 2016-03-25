package org.kurron.example.inbound.amqp

import org.junit.experimental.categories.Category
import org.kurron.categories.InboundIntegrationTest
import org.kurron.example.Application
import org.kurron.example.ApplicationProperties
import org.kurron.example.inbound.rest.RestCapable
import org.kurron.traits.GenerationAbility
import org.springframework.amqp.core.MessageDeliveryMode
import org.springframework.amqp.core.MessagePropertiesBuilder
import org.springframework.amqp.rabbit.core.RabbitAdmin
import org.springframework.amqp.rabbit.core.RabbitOperations
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.IntegrationTest
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

/**
 * An integration-level test of the RabbitGateway object.
 **/
@Category( InboundIntegrationTest )
@IntegrationTest
@ContextConfiguration( classes = [Application,TestConfiguration], loader = SpringApplicationContextLoader )
class RabbitGatewayIntegrationTest extends Specification implements GenerationAbility, RestCapable {

    @Autowired
    ApplicationProperties configuration

    @Autowired
    RabbitAdmin administrator

    @Autowired
    RabbitOperations template

    @Autowired
    Jackson2JsonMessageConverter converter

    def setup() {
        // clear the queue before each test
        assert configuration
        assert administrator
        administrator.purgeQueue( configuration.queueName, false )
    }

    def 'exercise publishing happy path'() {
        given: 'a proper testing environment'
        assert template
        assert converter

        and: 'a valid message'
        def properties = MessagePropertiesBuilder.newInstance()
                .setAppIdIfAbsent( 'integration test' )
                .setContentTypeIfAbsentOrDefault( 'test/plain' )
                .setDeliveryModeIfAbsentOrDefault( MessageDeliveryMode.NON_PERSISTENT )
                .setMessageIdIfAbsent( randomUUID() as String )
                .setTimestampIfAbsent( Calendar.instance.time )
                .setTypeIfAbsent( 'sample command' )
                .build()
        def message = converter.toMessage( new SampleRequest( status: randomPositiveInteger(), timestamp: randomHexString() ), properties )

        when: 'message is sent'
        template.send( configuration.exchangeName, configuration.queueName, message )

        then:
        Thread.sleep( 1000 )
    }

}
