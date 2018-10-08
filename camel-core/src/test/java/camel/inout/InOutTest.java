package camel.inout;

import org.apache.camel.RoutesBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

public class InOutTest extends CamelTestSupport {

    @Override
    protected RoutesBuilder createRouteBuilder() {
        return new InOutBuilder();
    }

    @Test
    public void TestOutMessage() {
        Object body = null;
        for (int i = 0; i < 1000; i++) {
            body = template.requestBody("seda:test", "Hello");
        }
        System.out.println(body);
        assertEquals(body, "Hello World again");
    }
}
