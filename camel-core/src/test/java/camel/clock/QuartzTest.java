package camel.clock;

import ch.qos.logback.core.util.TimeUtil;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class QuartzTest extends CamelTestSupport {

    @Override
    protected RoutesBuilder createRouteBuilder() throws Exception {
        return new QuartzRoute();
    }

    @Test
    public void testQuartz() throws InterruptedException {
        Thread.currentThread().sleep(TimeUnit.MINUTES.toMillis(1));
    }
}
