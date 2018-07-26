package camel.enrich;

import org.apache.camel.RoutesBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

public class EnrichTest extends CamelTestSupport {
    @Override
    protected RoutesBuilder createRouteBuilder() throws Exception {
        return new EnrichEip();
    }

    @Test
    public void testEnrich() {
        Object object = template.requestBody("direct:start", "Hello");
        assertEquals(object, "Hello World");
    }
}
