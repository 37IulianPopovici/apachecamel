package components.netty;

import org.apache.camel.builder.RouteBuilder;

/**
 * @author Adam Ostrozlik
 * @version 1.0
 * @since 04.10.2018
 **/
public class NettyRouteBuilder extends RouteBuilder {
    @Override
    public void configure() {
        from("netty4-http:http://{{netty4socket}}/endpoint")
                .setBody(simple("${body}:${headers}\\n"))
                .to("stream:out?");
    }
}
