package org.kurron.example.inbound.amqp

import org.springframework.amqp.rabbit.core.RabbitManagementTemplate
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Beans only required for testing.
 */
@Configuration
class TestConfiguration {

    @Bean
    RabbitManagementTemplate rabbitManagementTemplate() {
        new RabbitManagementTemplate()
    }
}
