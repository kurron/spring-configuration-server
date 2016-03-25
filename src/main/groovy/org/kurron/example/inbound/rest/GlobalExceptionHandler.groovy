package org.kurron.example.inbound.rest

import org.kurron.example.MessagingContext
import org.kurron.feedback.FeedbackAware
import org.kurron.feedback.FeedbackProvider
import org.kurron.feedback.NullFeedbackProvider
import org.kurron.feedback.exceptions.AbstractError
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

import static org.apache.commons.lang3.exception.ExceptionUtils.getRootCauseMessage
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR

/**
 * Global handling for REST exceptions.
 */
@ControllerAdvice
@SuppressWarnings( ['DuplicateStringLiteral', 'GroovyUnusedDeclaration'] )
class GlobalExceptionHandler extends ResponseEntityExceptionHandler implements FeedbackAware {

    /**
     * The provider to use.
     */
    private FeedbackProvider theFeedbackProvider = new NullFeedbackProvider()

    @Override
    FeedbackProvider getFeedbackProvider() {
        theFeedbackProvider
    }

    @Override
    void setFeedbackProvider( final FeedbackProvider aProvider ) {
        theFeedbackProvider = aProvider
    }

    @Override
    protected ResponseEntity<HypermediaControl> handleExceptionInternal( Exception e,
                                                                         Object body,
                                                                         HttpHeaders headers,
                                                                         HttpStatus status,
                                                                         WebRequest request ) {
        theFeedbackProvider.sendFeedback( MessagingContext.GENERIC_ERROR, e.message )
        def control = new HypermediaControl( status: status.value() )
        control.errorBlock = new ErrorBlock( code: MessagingContext.GENERIC_ERROR.code,
                                             message: e.message,
                                             developerMessage: getRootCauseMessage( e ) )
        wrapInResponseEntity( control, status, headers )
    }

    /**
     * Handles errors thrown by application itself.
     * @param e the error.
     * @return the constructed response entity, containing details about the error.
     */
    @ExceptionHandler( AbstractError )
    static ResponseEntity<HypermediaControl> handleApplicationException( AbstractError e ) {
        def control = new HypermediaControl( status: e.httpStatus.value() ).with {
            errorBlock = new ErrorBlock( code: e.code, message: e.message, developerMessage: e.developerMessage )
            it
        }
        wrapInResponseEntity( control, e.httpStatus )
    }

    /**
     * Knows how to transform a non-application exception into a hypermedia control.
     * @param throwable non-application error.
     * @return control that contains as much data about the error that is available to us.
     */
    @ExceptionHandler( Throwable )
    static ResponseEntity<HypermediaControl> handleSystemException( final Throwable throwable ) {
        def control = new HypermediaControl( status: INTERNAL_SERVER_ERROR.value() )
        control.errorBlock = new ErrorBlock( code: MessagingContext.GENERIC_ERROR.code,
                message: throwable.message,
                developerMessage: getRootCauseMessage( throwable ) )
        wrapInResponseEntity( control, INTERNAL_SERVER_ERROR )
    }

    /**
     * Wraps the provided control in a response entity.
     * @param control the control to return in the body of the response.
     * @param status the HTTP status to return.
     * @param headers the HTTP headers to return. If provided, the existing headers are used, otherwise new headers are created.
     * @return the response entity.
     */
    private static ResponseEntity<HypermediaControl> wrapInResponseEntity( HypermediaControl control,
                                                                           HttpStatus status,
                                                                           HttpHeaders headers = new HttpHeaders() ) {
        new ResponseEntity<>( control, headers, status )
    }
}
