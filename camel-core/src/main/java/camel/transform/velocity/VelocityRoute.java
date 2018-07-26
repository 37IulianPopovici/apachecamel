package camel.transform.velocity;

import org.apache.camel.builder.RouteBuilder;

public class VelocityRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("direct:start")
            .setHeader("name", simple("Adam"))
            .to("velocity://velocity/file");
    }
}
