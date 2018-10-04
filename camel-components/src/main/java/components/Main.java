package components;


import components.netty.NettyRouteBuilder;

/**
 * @author Adam Ostrozlik
 * @version 1.0
 * @since 02.10.2018
 **/
public class Main {
    public static void main(String[] args) throws Exception {
        org.apache.camel.main.Main main = new org.apache.camel.main.Main();

        main.setPropertyPlaceholderLocations("test.properties");

        //main.addRouteBuilder(new FileRouteBuilder());
        main.addRouteBuilder(new NettyRouteBuilder());

        main.run();
    }
}
