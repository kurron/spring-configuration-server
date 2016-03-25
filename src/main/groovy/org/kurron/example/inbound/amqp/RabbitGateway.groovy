package org.kurron.example.inbound.amqp

import org.kurron.stereotype.InboundGateway
import org.springframework.amqp.rabbit.annotation.RabbitListener

/**
 * Gateway that handles incoming messages from a message queue.
 */
@InboundGateway
class RabbitGateway {

    @SuppressWarnings(['GrMethodMayBeStatic', 'GroovyUnusedDeclaration'])
    @RabbitListener( queues = '${example.queueName}' )
    void processMessage(SampleRequest  request ) {
        println "Request ${request} recieved"
        throw new UnsupportedOperationException( 'forced to fail' )
    }
}
