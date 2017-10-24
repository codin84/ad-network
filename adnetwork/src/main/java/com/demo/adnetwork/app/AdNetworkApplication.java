package com.demo.adnetwork.app;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;

@SpringBootApplication(scanBasePackages = {"com.demo.adnetwork"})
@EnableJpaRepositories("com.demo.adnetwork")
@EntityScan(basePackages = {"com.demo.adnetwork"})
public class AdNetworkApplication implements CommandLineRunner
{
    public static void main(String[] args) throws Exception
    {
        SpringApplication.run(AdNetworkApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception
    {
    }

    @Bean
    public Java8TimeDialect java8TimeDialect()
    {
        return new Java8TimeDialect();
    }
}