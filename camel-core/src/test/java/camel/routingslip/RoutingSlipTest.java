package camel.routingslip;

import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.RouteBuilder;
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
public class RoutingSlipTest extends CamelTestSupport {

    @Test
    public void testRoutingSlip() throws InterruptedException {
        MockEndpoint mock1 = getMockEndpoint("mock:a");
        MockEndpoint mock2 = getMockEndpoint("mock:b");
        MockEndpoint mock3 = getMockEndpoint("mock:c");

        mock1.expectedBodiesReceived("Hello World");
        mock2.expectedBodiesReceived("Hello World again");
        mock3.expectedBodiesReceived("Hello World again again");

        Map<String,Object> headers = new HashMap<>();
        headers.put("camel/routingslip", "direct:a,direct:b,direct:c");

        template.sendBodyAndHeaders("seda:input", "Hello World", headers);

        assertMockEndpointsSatisfied(2, TimeUnit.SECONDS);
    }

    @Override
    protected RoutesBuilder[] createRouteBuilders() {
        return new RoutesBuilder[] {
                new RoutingSlipRouteBuilder(),
                new RouteBuilder() {
                    @Override
                    public void configure() {
                        from("direct:a").setBody(simple("${body}")).to("mock:a");
                    }
                },
                new RouteBuilder() {
                    @Override
                    public void configure() {
                        from("direct:b").setBody(simple("${body} again")).to("mock:b");
                    }
                },
                new RouteBuilder() {
                    @Override
                    public void configure() {
                        from("direct:c").setBody(simple("${body} again")).to("mock:c");
                    }
                }
        };
    }
}
