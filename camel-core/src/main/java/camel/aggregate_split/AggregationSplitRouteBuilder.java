package camel.aggregate_split;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.hawtdb.HawtDBAggregationRepository;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Adam Ostrozlik
 * @version 1.0
 * @since 09.10.2018
 **/
public class AggregationSplitRouteBuilder extends RouteBuilder {

    private static final Logger logger = LoggerFactory.getLogger(AggregationSplitRouteBuilder.class);

    @Override
    public void configure() {
        // camel.aggregate_split requires keeping messages in memory so to persist that behavior, use repository as follows:
        HawtDBAggregationRepository myRepo = new HawtDBAggregationRepository("myrepo", "target/myrepo");
        myRepo.setUseRecovery(true);
        myRepo.setMaximumRedeliveries(5);

        from("seda:aggregate")
                .log(LoggingLevel.INFO, logger, "Sending ${body} with correlation key ${header.key}")
                .aggregate(header("key"), new MyAggregationStrategy())
                    .completionSize(3).completionTimeout(3000).aggregationRepository(myRepo)
                    .log(LoggingLevel.INFO, logger, "Completed by ${property.CamelAggregatedCompletedBy}")
                    .log(LoggingLevel.INFO, logger, "Sending out ${body}")
                    .to("mock:result");

        from("seda:split")
                .split(body().tokenize(""), new MyAggregationStrategyDelimited())
                    //.method(SplitterBean.class, "split") // another splitting method without using tokenize
                    .streaming()
                    //.parallelProcessing() // second part of test will not work if enabled
                    .stopOnException()
                    .log(LoggingLevel.INFO, logger, "Split message body is ${body}")
                    .to("mock:result2")
                    .setBody(simple("Updated ${body}"))
                    .log(LoggingLevel.INFO, logger, "Split message body is ${body}")
                .end()
                .log(LoggingLevel.INFO, logger, "Done splitting message with body ${body}")
                .to("mock:result3");
    }

    private class MyAggregationStrategy implements AggregationStrategy {
        @Override
        public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
            if (oldExchange == null) {
                return newExchange;
            }
            String oldBody = oldExchange.getIn().getBody(String.class);
            String newBody = newExchange.getIn().getBody(String.class);
            String body = oldBody + newBody;
            oldExchange.getIn().setBody(body);
            return oldExchange;
        }
    }
    private class MyAggregationStrategyDelimited implements AggregationStrategy {
        @Override
        public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
            if (newExchange.getException() != null) { // propagating exception back
                if (oldExchange == null) {
                    return newExchange;
                } else {
                    oldExchange.setException(newExchange.getException());
                }
            }
            if (oldExchange == null) {
                return newExchange;
            }
            String oldBody = oldExchange.getIn().getBody(String.class);
            String newBody = newExchange.getIn().getBody(String.class);
            String body = oldBody + "," + newBody;
            oldExchange.getIn().setBody(body);
            return oldExchange;
        }
    }
}
