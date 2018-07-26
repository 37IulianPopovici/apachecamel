package camel.inout;

import org.apache.camel.RoutesBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

public class InOutTest extends CamelTestSupport {

    @Override
    protected RoutesBuilder createRouteBuilder() throws Exception {
        return new InOutBuilder();
    }

    @Test
    public void TestOutMessage() {
        Object body = template.requestBody("direct:test", "Hello");
        System.out.println(body);
        assertEquals(body, "Hello World");
    }
}
