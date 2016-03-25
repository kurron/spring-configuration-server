package org.kurron.example.inbound.rest

import java.time.Instant
import javax.servlet.http.HttpServletRequest
import org.kurron.example.MessagingContext
import org.kurron.example.core.TimeComponent
import org.kurron.feedback.AbstractFeedbackAware
import org.kurron.stereotype.InboundRestGateway
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.servlet.HandlerMapping

/**
 * Inbound HTTP gateway that supports the FIXME resource.
 **/
@InboundRestGateway
@RequestMapping
class RestGateway extends AbstractFeedbackAware {

    /**
     * Knows how to get the most accurate time.
     */
    private final TimeComponent theComponent

    @Autowired
    RestGateway( final TimeComponent aComponent ) {
        theComponent = aComponent
    }

    @RequestMapping( path = '/descriptor/application', method = [RequestMethod.GET], produces = [HypermediaControl.MIME_TYPE] )
    ResponseEntity<HypermediaControl> fetchApplicationList( HttpServletRequest request ) {
        def control = defaultControl( request )
        control.time = theComponent.currentTime().toString()
        ResponseEntity.ok( control )
    }

    @RequestMapping( path = '/descriptor/fail', method = [RequestMethod.GET], produces = [HypermediaControl.MIME_TYPE] )
    ResponseEntity<HypermediaControl> failApplicationList() {
        throw new RuntimeException( 'Failure -- system exception' )
    }

    @RequestMapping( path = '/descriptor/fail/application', method = [RequestMethod.GET], produces = [HypermediaControl.MIME_TYPE] )
    ResponseEntity<HypermediaControl> failSystemApplicationList() {
        throw new ForcedApplicationError( MessagingContext.FORCED_ERROR )
    }

    protected static HypermediaControl defaultControl( HttpServletRequest request ) {
        def path = request.getAttribute( HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE ) as String
        new HypermediaControl( status: HttpStatus.OK.value(), timestamp: Instant.now().toString(), path: path )
    }

}
