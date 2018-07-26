package camel.transform.xml;

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
                .bean(new XmlExtractor())
                .bean(new LoggingBean(), "logBodyXml")
            .end();
    }

    private class StringAggregator implements AggregationStrategy {

        @Override
        public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
            if (Objects.isNull(oldExchange)){
                return newExchange;
            }
            String newBody = newExchange.getIn().getBody().toString();
            newBody = newBody.replaceAll("<\\?xml version=.*?>", "");
            String outBody = oldExchange.getIn().getBody().toString() + "\n" + newBody;
            newExchange.getIn().setBody(outBody);
            return newExchange;
        }
    }
}
