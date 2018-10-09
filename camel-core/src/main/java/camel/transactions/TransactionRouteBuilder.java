package camel.transactions;

import org.apache.camel.builder.RouteBuilder;

/**
 * @author Adam Ostrozlik
 * @version 1.0
 * @since 09.10.2018
 **/
public class TransactionRouteBuilder extends RouteBuilder {
    @Override
    public void configure() {
        from("activemq:queue:firstQueue")
                .transacted()
                .to("direct:a");

        from("direct:a") // must be in same thread for transactions to work
                .log("Message read: ${body}")
                .choice().when(header("JMSRedelivered").isEqualTo("false"))
                .throwException(new NullPointerException("Exception throws"))
                .otherwise()
                .to("mock:result")
                .end();
    }
}
