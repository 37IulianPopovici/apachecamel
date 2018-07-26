package camel.clock;

import camel.logging.LoggingBean;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class TimerRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        //@formatter:off
        from("timer:timerTick?period=1000")
            .id("TimerRoute")
            .bean(new LoggingBean());
        //@formatter:on
    }
}
