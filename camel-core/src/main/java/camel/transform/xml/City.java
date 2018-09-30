package camel.transform.xml;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

// jaxb
@XmlRootElement
@XmlType(propOrder = {"name", "population"})
// lombok
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class City implements Serializable {
    private String name;
    private long population;
}
