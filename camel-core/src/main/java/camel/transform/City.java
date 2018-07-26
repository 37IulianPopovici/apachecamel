package camel.transform;

import lombok.*;

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
