package camel.enrich;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.processor.aggregate.AggregationStrategy;

public class EnrichEip extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("direct:start")
            .enrich("seda:enricher", new Aggregation());

        from("seda:enricher")
            .setBody(simple("World"));
    }

    private class Aggregation implements AggregationStrategy {

        @Override
        public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
            oldExchange.getIn().setBody(oldExchange.getIn().getBody() + " " + newExchange.getIn().getBody());
            return oldExchange;
        }
    }
}
