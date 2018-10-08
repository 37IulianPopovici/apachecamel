package camel.inout;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class InOutBuilder extends RouteBuilder {
    @Override
    public void configure() {
        from("seda:test?concurrentConsumers=10")
                .inOut("direct:first")
                .setBody(simple("${body} again"));

        from("direct:first")
                .inOut("direct:second");

        from("direct:second")
                .inOut("direct:third");

        from("direct:third")
                .log(">>>>>>> Message received")
                .transform(simple("${body} World"));
    }
}
