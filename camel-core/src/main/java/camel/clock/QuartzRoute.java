package camel.clock;

import camel.logging.LoggingBean;
import org.apache.camel.builder.RouteBuilder;

public class QuartzRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("quartz2://report?cron=*+*+*+*+*+?")
            .wireTap("direct:tap");
        from("direct:tap")
            .bean(new LoggingBean(), "logQuartzTick");
    }
}
