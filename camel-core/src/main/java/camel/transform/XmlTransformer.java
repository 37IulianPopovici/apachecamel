package camel.transform;

import org.apache.camel.Body;
import org.apache.camel.Exchange;
import org.apache.camel.Handler;
import org.apache.camel.Message;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.ByteArrayOutputStream;

public class XmlTransformer {

    @Handler
    public void transform(@Body City city, Exchange exchange) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(City.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        ByteArrayOutputStream stringBytes = new ByteArrayOutputStream();
        marshaller.marshal(city, stringBytes);
        exchange.getIn().setBody(new String(stringBytes.toByteArray()));
    }
}
