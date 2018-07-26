package camel.exceptions;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Predicate;
import org.apache.camel.builder.RouteBuilder;

import java.util.Objects;

public class ExceptionRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("direct:start")
            .errorHandler(deadLetterChannel("direct:dlq")
                .redeliveryDelay(2000)
                .retryAttemptedLogLevel(LoggingLevel.WARN)
                .retryWhile(simple("${header.CamelRedeliveryCounter} <= 3")))

            .onException(Exception.class)
                .maximumRedeliveries(2)
                .redeliveryDelay(2000)
                .retryAttemptedLogLevel(LoggingLevel.WARN)
            .end()

            .bean(new ExceptionRaise())
            .setBody(simple("Message redelivered"));

        from("direct:dlq")
            .log(">>>>> Message in DLQ");
    }
}
