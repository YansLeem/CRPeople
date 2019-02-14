package com.example.Lab.People;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(PersonRepository repository) throws Exception{
        return args -> {
            log.info("Preloading" + repository.save(new Person(12335L,"Paul","Walker")));
            log.info("Preloading" + repository.save(new Person(77777L,"Stan","Smith")));
        };
    }
}
