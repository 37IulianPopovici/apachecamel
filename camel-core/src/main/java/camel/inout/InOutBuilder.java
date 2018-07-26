package camel.inout;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class InOutBuilder extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("direct:test")
            .transform(simple("${body} World"))
            .inOnly("seda:log");

        from("seda:log")
            .log("Message logged: ${body}");
    }
}
