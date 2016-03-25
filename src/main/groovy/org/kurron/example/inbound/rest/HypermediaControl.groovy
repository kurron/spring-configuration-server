package org.kurron.example.inbound.rest

import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.Canonical
import javax.validation.constraints.NotNull
import org.hibernate.validator.constraints.NotEmpty
import org.springframework.hateoas.ResourceSupport
import org.springframework.http.MediaType

/**
 * The hypermedia object returned by all services.
 **/
@Canonical
class HypermediaControl extends ResourceSupport {

    /**
     * The string form of the control's MIME-TYPE.
     */
    //  static final String MIME_TYPE = 'application/hal+json'
    static final String MIME_TYPE = 'application/json;type=FIXME;version=1.0.0'

    /**
     * The object form of the control's MIME-TYPE.
     */
    static final MediaType MEDIA_TYPE = MediaType.parseMediaType( MIME_TYPE )

    /**
     * HTTP status of the service, response-only.
     */
    @JsonProperty( 'status' )
    Integer status

    /**
     * ISO 8601 timestamp of when the service completed, response-only.
     */
    @JsonProperty( 'timestamp' )
    String timestamp

    /**
     * Relative path of the completed service, response-only.
     */
    @JsonProperty( 'path' )
    String path


    /**
     * Output only, where fault information is stored.
     */
    @JsonProperty( 'error' )
    ErrorBlock errorBlock

    // ------------------ these are just example properties, change them as needed -------------

    /**
     * Output only, the current time in ISO 8601 format.
     */
    @JsonProperty( 'time' )
    @NotNull
    String time

    /**
     * Input only, the Base 64 encoded Docker Compose fragment.
     */
    @JsonProperty( 'fragment' )
    @NotNull
    String fragment

    /**
     * As input, the collection of applications this fragment should be associated with. As output, the collection of all available applications.
     */
    @JsonProperty( 'applications' )
    @NotNull
    @NotEmpty
    List<String> applications

    /**
     * As input, the single release the fragment should be associated with. As output, the collection of all available releases.
     */
    @JsonProperty( 'releases' )
    @NotNull
    List<String> releases

    /**
     * Output only, the collection of all available versions.
     */
    @JsonProperty( 'versions' )
    List<Integer> versions

    /**
     * Output only, the Base 64 encoded Docker Compose descriptor.
     */
    @JsonProperty( 'descriptor' )
    String descriptor

}
