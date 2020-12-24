package com.github.vizaizai.scholar;

import com.github.vizaizai.boot.annotation.EnableEasyHttp;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableEasyHttp
public class ScholarApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScholarApplication.class, args);
    }

}
