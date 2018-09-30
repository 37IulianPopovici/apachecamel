package camel.exceptions;

import org.apache.camel.Exchange;
import org.apache.camel.Handler;

public class ExceptionRaise {

    private static int counter = 0;

    @Handler
    public void raiseException(Exchange exchange) throws Exception {
        if (counter++ < 3) {
            throw new Exception("Test exception");
        }
    }

}
