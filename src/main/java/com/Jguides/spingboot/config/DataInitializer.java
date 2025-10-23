package com.Jguides.spingboot.config;

import com.Jguides.spingboot.Model.User;
import com.Jguides.spingboot.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner seedUsers(UserRepository repo, PasswordEncoder encoder) {
        return args -> {
            // admin (ROLE_ADMIN_MANAGER)
            if (!repo.existsByUserName("admin")) {
                User u = new User();
                u.setUserName("admin");
                u.setEmail("admin@example.com");
                u.setFullName("Admin Manager");
                u.setRole("ADMIN_MANAGER"); // maps to ROLE_ADMIN_MANAGER
                u.setPasswordHash(encoder.encode("admin123"));
                repo.save(u);
            }

            // store (ROLE_STORE_MANAGER)
            if (!repo.existsByUserName("store")) {
                User u = new User();
                u.setUserName("store");
                u.setEmail("store@example.com");
                u.setFullName("Store Manager");
                u.setRole("STORE_MANAGER");
                u.setPasswordHash(encoder.encode("store123"));
                repo.save(u);
            }

            // user (ROLE_USER)
            if (!repo.existsByUserName("user")) {
                User u = new User();
                u.setUserName("user");
                u.setEmail("user@example.com");
                u.setFullName("Regular User");
                u.setRole("USER");
                u.setPasswordHash(encoder.encode("user123"));
                repo.save(u);
            }
        };
    }
}
