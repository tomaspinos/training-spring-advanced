package cz.profinit.training.springadvanced.springws.vat;

import java.util.List;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

import cz.profinit.training.springadvanced.springws.vat.endpoint.interceptor.CountingEndpointInterceptor;

@EnableWs
@Configuration
@ComponentScan
public class WebServiceConfig extends WsConfigurerAdapter {

    @Bean
    public ServletRegistrationBean messageDispatcherServlet(final ApplicationContext applicationContext) {
        final MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean(servlet, "/ws/*");
    }

    @Bean(name = "checkVatService")
    public DefaultWsdl11Definition defaultWsdl11Definition(final XsdSchema vatSchema) {
        final DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("checkVatPort");
        wsdl11Definition.setLocationUri("/ws");
        wsdl11Definition.setTargetNamespace("urn:ec.europa.eu:taxud:vies:services:checkVat:types");
        wsdl11Definition.setSchema(vatSchema);
        return wsdl11Definition;
    }

    @Bean
    public XsdSchema vatSchema() {
        return new SimpleXsdSchema(new ClassPathResource("checkVatService.xsd"));
    }

    @Override
    public void addInterceptors(final List<EndpointInterceptor> interceptors) {
        interceptors.add(new CountingEndpointInterceptor());
    }
}
