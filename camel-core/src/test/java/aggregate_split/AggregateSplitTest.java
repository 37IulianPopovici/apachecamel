package aggregate_split;

import camel.aggregate_split.AggregationSplitRouteBuilder;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Adam Ostrozlik
 * @version 1.0
 * @since 09.10.2018
 **/
public class AggregateSplitTest extends CamelTestSupport {

    @Test
    public void testAggregation() throws InterruptedException {
        MockEndpoint mockAggregate = getMockEndpoint("mock:result");
        mockAggregate.expectedBodiesReceived("ABC");

        Map<String, Object> data1 = new HashMap<>();
        Map<String, Object> data2 = new HashMap<>();
        Map<String, Object> data3 = new HashMap<>();
        Map<String, Object> data4 = new HashMap<>();

        data1.put("key", 1);
        data2.put("key", 1);
        data3.put("key", 2);
        data4.put("key", 1);

        template.sendBodyAndHeaders("seda:aggregate", "A", data1);
        template.sendBodyAndHeaders("seda:aggregate", "B", data2);
        template.sendBodyAndHeaders("seda:aggregate", "E", data3);
        template.sendBodyAndHeaders("seda:aggregate", "C", data4);

        assertMockEndpointsSatisfied();
    }

    @Test
    public void testSplitTest() throws InterruptedException {
        MockEndpoint mockSplit = getMockEndpoint("mock:result2");
        MockEndpoint mockSplitUpdateAggragate = getMockEndpoint("mock:result3");

        mockSplit.expectedBodiesReceivedInAnyOrder("A","B","C");
        mockSplitUpdateAggragate.expectedBodiesReceived("Updated A,Updated B,Updated C");

        template.sendBody("seda:split","ABC");
        assertMockEndpointsSatisfied(5000, TimeUnit.MILLISECONDS);
    }

    @Override
    protected RoutesBuilder createRouteBuilder() throws Exception {
        return new AggregationSplitRouteBuilder();
    }
}
