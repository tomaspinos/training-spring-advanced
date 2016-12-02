package cz.profinit.training.springadvanced.warmup;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class WarmupApplication {

    public static void main(String[] args) {
        final AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(WarmupConfiguration.class);

        final WarmupService warmupService = applicationContext.getBean(WarmupService.class);

        System.out.println(warmupService.sayHello("world"));
    }
}
