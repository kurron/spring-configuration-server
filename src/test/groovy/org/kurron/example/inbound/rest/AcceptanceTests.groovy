package org.kurron.example.inbound.rest

import cucumber.api.CucumberOptions
import cucumber.api.junit.Cucumber
import org.junit.runner.RunWith

/**
 * Driver for all acceptance tests.
 */
@CucumberOptions( strict = false,
        tags = ['~@optional'], // by default do not run the optional tests
        plugin = ['pretty', 'html:build/reports/acceptanceTests'],
        monochrome = true )
@RunWith( Cucumber )
class AcceptanceTests {
    // empty -- the magic happens elsewhere -- this is just a driver
}
