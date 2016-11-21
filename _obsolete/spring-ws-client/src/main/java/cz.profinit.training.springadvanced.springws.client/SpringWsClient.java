package cz.profinit.training.springadvanced.springws.client;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.ws.client.core.WebServiceTemplate;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;

/**
 * Hello world!
 */
public class SpringWsClient {
    private static final String MESSAGE = "<sch:GetListIdsRequest xmlns:sch=\"http://profinit.cz/springadvanced/schemas\"/>";

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("springws-client-context.xml");

        WebServiceTemplate wst = context.getBean("webServiceTemplate", WebServiceTemplate.class);

        StreamSource source = new StreamSource(new StringReader(MESSAGE));
        StreamResult result = new StreamResult(System.out);
        wst.sendSourceAndReceiveToResult(source, result);
    }
}
