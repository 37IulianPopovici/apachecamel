package camel.clock;

import org.apache.camel.RoutesBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class QuartzTest extends CamelTestSupport {

    @Override
    protected RoutesBuilder createRouteBuilder() {
        return new QuartzRoute();
    }

    @Test
    public void testQuartz() throws InterruptedException {
        Thread.sleep(TimeUnit.MINUTES.toMillis(1));
    }
}
