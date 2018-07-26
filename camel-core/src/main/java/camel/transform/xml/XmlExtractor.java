package camel.transform.xml;

import org.apache.camel.Handler;
import org.apache.camel.language.XPath;

public class XmlExtractor {

    @Handler
    public void extractXml(@XPath("/city/name") String cityName) {
        System.out.println(cityName);
    }

}
