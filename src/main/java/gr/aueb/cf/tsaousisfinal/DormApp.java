package gr.aueb.cf.tsaousisfinal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class DormApp {

    public static void main(String[] args) {
        SpringApplication.run(DormApp.class, args);
    }

}
