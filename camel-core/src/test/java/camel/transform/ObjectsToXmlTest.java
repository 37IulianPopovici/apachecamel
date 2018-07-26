package camel.transform;

import camel.transform.xml.City;
import camel.transform.xml.ObjectsToXmlRoute;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.LinkedList;

public class ObjectsToXmlTest extends CamelTestSupport {

    private Collection<City> cities;

    @Override
    protected RoutesBuilder createRouteBuilder() throws Exception {
        return new ObjectsToXmlRoute();
    }

    @Before
    public void initCities() {
        cities = new LinkedList<>();
        cities.add(new City("Praha", 1000000));
        cities.add(new City("Kolín", 25000));
        cities.add(new City("Brno", 500000));
    }

    @Test
    public void transformTest() {
        Object body = template.requestBody("direct:start", cities);
        System.out.println("\n\n"+body.toString()+"\n\n");
    }
}
