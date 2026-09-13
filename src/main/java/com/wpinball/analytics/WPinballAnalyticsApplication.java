package com.wpinball.analytics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WPinballAnalyticsApplication {

    public static void main(String[] args) {
        SpringApplication.run(WPinballAnalyticsApplication.class, args);
    }

}
