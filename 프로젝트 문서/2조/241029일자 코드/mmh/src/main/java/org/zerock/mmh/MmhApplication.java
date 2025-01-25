package org.zerock.mmh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MmhApplication {

    public static void main(String[] args) {
        SpringApplication.run(MmhApplication.class, args);
    }

}
