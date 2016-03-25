package org.kurron.example.inbound.amqp

import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.Canonical

/**
 * An example message meant to get converted into JSON.
 */
@Canonical
class SampleRequest {

    @JsonProperty( 'status' )
    Integer status

    @JsonProperty( 'timestamp' )
    String timestamp
}
