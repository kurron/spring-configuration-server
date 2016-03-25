package org.kurron.example

import org.kurron.feedback.Audience
import org.kurron.feedback.FeedbackContext
import org.kurron.feedback.FeedbackLevel

/**
 * The enumeration of all logged messages produced by the system.
 **/
enum MessagingContext implements FeedbackContext {

    CURRENT_TIME( 1000, FeedbackLevel.WARN, Audience.QA, 'The current time is {}.' ),
    FORCED_ERROR( 1001, FeedbackLevel.ERROR, Audience.OPERATIONS, 'I was forced to fail!' ),
    GENERATED_TRACING_HEADER( 1002, FeedbackLevel.DEBUG, Audience.DEVELOPMENT, 'Generated trace id for OPTIONS call: {} = {}' ),
    MISSING_HTTP_HEADER_ERROR( 1003, FeedbackLevel.WARN, Audience.QA, 'Required {} header is missing!' ),
    GENERIC_ERROR( 1007, FeedbackLevel.ERROR, Audience.QA, 'The following error has occurred and was caught by the global error handler: {}',  )

    private final int code
    private final String formatString
    private final FeedbackLevel feedbackLevel
    private final Audience audience

    MessagingContext( final int aCode,
                      final FeedbackLevel aFeedbackLevel,
                      final Audience aAudience,
                      final String aFormatString ) {
        code = aCode
        formatString = aFormatString
        feedbackLevel = aFeedbackLevel
        audience = aAudience
    }

    @Override
    int getCode() {
        code
    }

    @Override
    String getFormatString() {
        formatString
    }

    @Override
    FeedbackLevel getFeedbackLevel() {
        feedbackLevel
    }

    @Override
    Audience getAudience() {
        audience
    }
}
