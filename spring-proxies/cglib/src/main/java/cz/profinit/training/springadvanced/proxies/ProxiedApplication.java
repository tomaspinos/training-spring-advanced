package cz.profinit.training.springadvanced.proxies;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ProxiedApplication {

    private final ProxiedService proxiedService;

    public ProxiedApplication(ProxiedService proxiedService) {
        this.proxiedService = proxiedService;
    }

    public static void main(final String[] args) {
        SpringApplication.run(ProxiedApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner() {
        return args -> System.out.println(proxiedService.a());
    }
}
