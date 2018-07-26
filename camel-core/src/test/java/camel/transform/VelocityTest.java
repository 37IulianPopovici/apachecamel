package camel.transform;

import camel.transform.velocity.VelocityRoute;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

public class VelocityTest extends CamelTestSupport {

    @Override
    protected RoutesBuilder createRouteBuilder() throws Exception {
        return new VelocityRoute();
    }

    @Test
    public void testVelocity() {
        Object body = template.requestBody("direct:start", "");
        System.out.println(body.toString());
    }
}
