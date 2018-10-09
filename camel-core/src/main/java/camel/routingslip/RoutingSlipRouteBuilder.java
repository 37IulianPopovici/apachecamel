package camel.routingslip;

import org.apache.camel.builder.RouteBuilder;

/**
 * @author Adam Ostrozlik
 * @version 1.0
 * @since 09.10.2018
 **/
public class RoutingSlipRouteBuilder extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("seda:input")
            .routingSlip(header("routingslip"), ",");
    }
}
