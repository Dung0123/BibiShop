package com.poly.BibiStore;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication()
public class BibiStoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(BibiStoreApplication.class, args);
    }
    @Component
    @RequiredArgsConstructor
    public class TEST implements ApplicationRunner {
        public void run(ApplicationArguments args) throws Exception {

            System.out.println("Running.....");
        }
    }

}
