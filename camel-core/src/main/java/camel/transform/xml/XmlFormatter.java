package camel.transform.xml;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

public class XmlFormatter {

    public static String formatXml(String unformattedXml) {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        String formattedXml = unformattedXml;
        try {
            DocumentBuilder documentBuilder = builderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(new InputSource(new StringReader(unformattedXml)));
            OutputFormat outputFormat = new OutputFormat(document);
            outputFormat.setLineWidth(65);
            outputFormat.setIndenting(true);
            outputFormat.setIndent(2);
            Writer out = new StringWriter();
            XMLSerializer xmlSerializer = new XMLSerializer(out, outputFormat);
            xmlSerializer.serialize(document);
            formattedXml = out.toString();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return formattedXml;
        }
    }
}
