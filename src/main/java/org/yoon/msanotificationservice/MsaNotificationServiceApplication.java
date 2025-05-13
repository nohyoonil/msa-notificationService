package org.yoon.msanotificationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class MsaNotificationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsaNotificationServiceApplication.class, args);
    }

}
