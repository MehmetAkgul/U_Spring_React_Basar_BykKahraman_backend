package com.hoaxify.backend;

import com.hoaxify.backend.user.User;
import com.hoaxify.backend.user.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication()
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    @Bean
    CommandLineRunner createInitialUsers(UserService userService) {
        return (args) -> {
            User user = new User();
            user.setUsername("User1");
            user.setDisplayName("display1");
            user.setPassword("P4ssword");
            userService.save(user);

        };
    }
}
