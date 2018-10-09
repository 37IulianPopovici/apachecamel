package camel.aggregate_split;

import org.apache.camel.Body;

import java.util.Arrays;
import java.util.List;

/**
 * @author Adam Ostrozlik
 * @version 1.0
 * @since 09.10.2018
 **/
public class SplitterBean {

    public static char[] split(@Body String body) {
        return body.toCharArray();
    }

    private SplitterBean() {
        // camel does not create instance to call static methods if the constructor is private
    }

}
