package org.trandy.trandy_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class TrandyServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrandyServerApplication.class, args);
    }

}
