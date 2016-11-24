package cz.profinit.training.springadvanced.integration;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource("classpath:spring/integration.xml")
public class Application {

    public static void main(String[] args) {
        new AnnotationConfigApplicationContext(Application.class);
    }


}
