package org.kurron.example.inbound.rest

import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.web.util.UriComponentsBuilder

/**
 * Traits shared by all test that are capable of exercising a REST API.
 **/
trait RestCapable {

    /**
     * Constructs a local URL, expanding and supplied path variables.
     * @param port the port the service is listening on.
     * @param path the path to expand.
     * @param variables any path variables that need to be inserted into the path.  Cannot be null.
     * @return fully constructed URI.
     */
    URI buildURI( int port, String path, Map variables ) {
        UriComponentsBuilder.newInstance().scheme( 'http' ).host( 'localhost' ).port( port ).path( path ).buildAndExpand( variables ).toUri()
    }

    /**
     * Constructs a request with only the proper headers filled in -- no body.
     * @return constructed request.
     */
    HttpEntity buildRequest() {
        def headers = new HttpHeaders()
        headers.setContentType( HypermediaControl.MEDIA_TYPE )
        headers.setAccept( [HypermediaControl.MEDIA_TYPE] )
        new HttpEntity( headers )
    }

    /**
     * Constructs a request with the headers and body properly filled in.
     * @param control the body of the request.
     * @return constructed request.
     */
    HttpEntity<HypermediaControl> buildRequest( HypermediaControl control ) {
        def headers = new HttpHeaders()
        headers.setContentType( HypermediaControl.MEDIA_TYPE )
        headers.setAccept( [HypermediaControl.MEDIA_TYPE] )
        new HttpEntity<HypermediaControl>( control, headers )
    }
}
