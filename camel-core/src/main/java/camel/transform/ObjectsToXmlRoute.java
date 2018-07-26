package camel.transform;

import camel.logging.LoggingBean;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.processor.aggregate.AggregationStrategy;

import java.util.Objects;

public class ObjectsToXmlRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("direct:start")
            .setExchangePattern(ExchangePattern.InOut)
            .split(body(), new StringAggregator())
                .parallelProcessing().streaming()
                .bean(new XmlTransformer())
                .bean(new LoggingBean(), "logBodyXml")
            .end();
    }

    private class StringAggregator implements AggregationStrategy {

        @Override
        public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
            if (Objects.isNull(oldExchange)){
                return newExchange;
            }
            String newBody = oldExchange.getIn().getBody().toString() + newExchange.getIn().getBody().toString();
            newExchange.getIn().setBody(newBody);
            return newExchange;
        }
    }
}
