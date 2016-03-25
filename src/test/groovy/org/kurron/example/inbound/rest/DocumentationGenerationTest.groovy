package org.kurron.example.inbound.rest

import org.junit.Rule
import org.junit.experimental.categories.Category
import org.kurron.categories.DocumentationTest
import org.kurron.example.Application
import org.kurron.traits.GenerationAbility
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.boot.test.WebIntegrationTest
import org.springframework.restdocs.RestDocumentation
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import spock.lang.Specification


import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

/**
 * This 'test' generates code snippets used in the REST documentation.
 *
 * See http://docs.spring.io/spring-restdocs/docs/1.0.1.RELEASE/reference/html5/ for details on how to use the documentation tool.
 **/
@Category( DocumentationTest )
@WebIntegrationTest( randomPort = true )
@ContextConfiguration( classes = Application, loader = SpringApplicationContextLoader )
class DocumentationGenerationTest extends Specification implements GenerationAbility {

    @Autowired
    private WebApplicationContext context

    @Rule
    public RestDocumentation documentation = new RestDocumentation( 'build/generated-snippets' )

    MockMvc mockMvc

    def setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup( context ).apply( documentationConfiguration( documentation ) ).build()
    }

    def 'demonstrate failure scenario'() {

        given: 'a valid request'
        def requestBuilder = get( '/descriptor/fail/application' ).accept( HypermediaControl.MEDIA_TYPE ).header( 'X-Correlation-Id', randomUUID() )

        when: 'the GET request is made'
        mockMvc.perform( requestBuilder ).andExpect( status().isIAmATeapot() ).andDo( document( 'failure-scenario' ) )

        then: 'examples are generated'
    }

    def 'demonstrate GET my resource'() {

        given: 'a valid request'
        def requestBuilder = get( '/descriptor/application' ).accept( HypermediaControl.MEDIA_TYPE ).header( 'X-Correlation-Id', randomUUID() )

        when: 'the GET request is made'
        mockMvc.perform( requestBuilder ).andExpect( status().isOk() ).andDo( document( 'get-my-resource' ) )

        then: 'examples are generated'
    }
}
