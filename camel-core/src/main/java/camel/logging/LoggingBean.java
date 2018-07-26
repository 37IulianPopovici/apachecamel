package camel.logging;

import camel.transform.xml.XmlFormatter;
import org.apache.camel.Body;
import org.apache.camel.Exchange;
import org.apache.camel.Header;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingBean {

    private final static Logger LOG = LoggerFactory.getLogger(LoggingBean.class);

    public void logTimerTick(@Header(Exchange.TIMER_COUNTER) String counter) {
        LOG.info("Timer tick number {}", counter);
    }

    public void logBodyXml(@Body Object body) {
        LOG.info(XmlFormatter.formatXml(body.toString()));
    }

    public void logQuartzTick() {
        LOG.info("Quartz tick");
    }
}
