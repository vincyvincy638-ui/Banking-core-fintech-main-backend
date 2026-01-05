package com.fintech;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.CommandLineRunner;

import com.fintech.entity.User;
import com.fintech.repository.UserRepository;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initUsers(UserRepository repo) {
        return args -> {

            if (!repo.existsByUsername("user")) {
                User user = new User();
                user.setUsername("user");
                user.setPassword("{noop}user123");
                user.setRole("ROLE_USER");
                repo.save(user);
            }

            if (!repo.existsByUsername("admin")) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword("{noop}admin123");
                admin.setRole("ROLE_ADMIN");
                repo.save(admin);
            }
        };
    }
}
