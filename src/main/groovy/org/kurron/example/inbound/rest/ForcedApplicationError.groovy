package org.kurron.example.inbound.rest

import groovy.transform.InheritConstructors
import org.kurron.feedback.exceptions.AbstractError
import org.springframework.http.HttpStatus

/**
 * The error is an example of a type that is due to application logic.
 */
@InheritConstructors
class ForcedApplicationError extends AbstractError {

    @Override
    HttpStatus getHttpStatus() {
        HttpStatus.I_AM_A_TEAPOT
    }

    @Override
    String getDeveloperMessage() {
        'The world is ending!'
    }
}
