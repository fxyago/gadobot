package br.com.yagofx.gadobot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@Slf4j
@SpringBootApplication
public class InitApp {

    @Value("${spring.datasource.url}")
    String url;

    @Value("${spring.datasource.username}")
    String username;

    public static void main(String[] args) {
        SpringApplication.run(InitApp.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ac) {



        return (args) -> {};
    }

}