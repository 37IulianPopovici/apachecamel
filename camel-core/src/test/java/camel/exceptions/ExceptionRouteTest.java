package camel.exceptions;

import org.apache.camel.RoutesBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

public class ExceptionRouteTest extends CamelTestSupport {

    @Override
    protected RoutesBuilder createRouteBuilder() throws Exception {
        return new ExceptionRoute();
    }

    @Test
    public void testExceptions() {
        Object body = template.requestBody("direct:start", "Some body");
        System.out.println(body.toString());
    }

}
