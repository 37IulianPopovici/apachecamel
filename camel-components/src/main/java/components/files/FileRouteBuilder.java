package components.files;

import org.apache.camel.builder.RouteBuilder;

/**
 * @author Adam Ostrozlik
 * @version 1.0
 * @since 02.10.2018
 **/
public class FileRouteBuilder extends RouteBuilder {
    @Override
    public void configure() {
        from("file:{{filepath}}?noop=true&recursive=true")
                .to("stream:out");

        from("stream:in?promptMessage=Enter something:")
                .setBody(simple("${body}\\n"))
                .to("file:{{filepath}}?fileExist=Append");
    }
}
