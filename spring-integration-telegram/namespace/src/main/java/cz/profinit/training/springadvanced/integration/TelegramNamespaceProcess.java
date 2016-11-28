package cz.profinit.training.springadvanced.integration;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TelegramNamespaceProcess {

    public static void main(final String[] args) {
        new ClassPathXmlApplicationContext("/telegram.xml");
    }
}
