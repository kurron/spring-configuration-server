Feature: Auditing Support
  In order to better understand how the service is being used by users,
  the system will emit events to a message broker as it processes conversions

  @required
  Scenario: Successful Conversion
    When a successful conversion has been executed
    And an event containing the ISO-8601 timestamp of the execution
    And an event containing the duration of the execution, in milliseconds
    And an event containing the value of the Accept header
    And an event containing the value of the Content-Length header
    And an event containing the value of the Content-Type header
    And an event containing the value of the X-Transform-To header
    And an event containing the value of the X-Uploaded-By header
    And an event containing the value of the X-Expiration-Minutes header
    And an event containing the size, in bytes, of the generated asset
    Then the event is emitted

  @required
  Scenario: Failed Conversion
    When a failed conversion has been executed
    And an event containing the ISO-8601 timestamp of the execution
    And an event containing the duration of the execution, in milliseconds
    And an event containing the value of the Accept header
    And an event containing the value of the Content-Length header
    And an event containing the value of the Content-Type header
    And an event containing the value of the X-Transform-To header
    And an event containing the value of the X-Uploaded-By header
    And an event containing the value of the X-Expiration-Minutes header
    And an event containing the message code of the failure
    Then the event is emitted
