package camel.clock;

import org.apache.camel.RoutesBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

public class TimerRouteTest extends CamelTestSupport {
    @Override
    protected RoutesBuilder createRouteBuilder() throws Exception {
        return new TimerRoute();
    }

    @Test
    public void routeBuilderLoad() throws InterruptedException {
        Thread.currentThread().sleep(10000);
    }
}
