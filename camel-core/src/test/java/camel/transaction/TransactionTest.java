package camel.transaction;

import camel.transactions.TransactionRouteBuilder;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.camel.component.ActiveMQComponent;
import org.apache.activemq.store.memory.MemoryPersistenceAdapter;
import org.apache.camel.CamelContext;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.NotifyBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.component.jms.JmsConfiguration;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.SimpleRegistry;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jms.connection.JmsTransactionManager;

import javax.jms.ConnectionFactory;
import java.util.concurrent.TimeUnit;

import static org.apache.activemq.camel.component.ActiveMQComponent.activeMQComponent;

/**
 * @author Adam Ostrozlik
 * @version 1.0
 * @since 09.10.2018
 **/
public class TransactionTest extends CamelTestSupport {

    private BrokerService broker;

    @Override
    protected CamelContext createCamelContext() {
        // setup activemq
        ActiveMQComponent activeMQComponent = activeMQComponent("tcp://localhost:61616");

        // setup jms
        ConnectionFactory activemqConnectionFactory = activeMQComponent.getConfiguration().getConnectionFactory();
        JmsConfiguration jmsConfiguration = new JmsConfiguration(activemqConnectionFactory);
        JmsTransactionManager transactionManager = new JmsTransactionManager(activemqConnectionFactory);
        jmsConfiguration.setTransactionManager(transactionManager);
        JmsComponent jmsComponent = new JmsComponent(jmsConfiguration);

        // setup registry
        SimpleRegistry simpleRegistry = new SimpleRegistry();
        simpleRegistry.put("transactionManager", transactionManager);

        // setup context
        CamelContext camelContext = new DefaultCamelContext(simpleRegistry);
        camelContext.addComponent("jms", jmsComponent);
        camelContext.addComponent("activemq", activeMQComponent);
        camelContext.setStreamCaching(true);
        return camelContext;
    }

    @Before
    public void setupBroker() throws Exception {
        broker = new BrokerService();
        broker.setUseJmx(true);
        broker.addConnector("tcp://localhost:61616");
        broker.setPersistent(false);
        broker.setPersistenceAdapter(new MemoryPersistenceAdapter());
        broker.start();
    }

    @After
    public void stopBroker() throws Exception {
        broker.stop();
    }

    @Test
    public void testTransaction() throws InterruptedException {
        MockEndpoint mock = context().getEndpoint("mock:result", MockEndpoint.class);
        mock.expectedHeaderReceived("JMSRedelivered", true);
        NotifyBuilder notifyBuilder = new NotifyBuilder(context);
        notifyBuilder
                .whenCompleted(1)
                .create();
        template.sendBody("activemq:queue:firstQueue", "Hello World");
        notifyBuilder.matches(5, TimeUnit.SECONDS);

        assertMockEndpointsSatisfied();
    }

    @Override
    protected RoutesBuilder[] createRouteBuilders() {
        return new RoutesBuilder[]{
                new TransactionRouteBuilder()
        };
    }
}
