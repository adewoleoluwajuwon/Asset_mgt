package com.Jguides.spingboot.config;

import com.Jguides.spingboot.Model.AssetStatus;
import com.Jguides.spingboot.Model.User;
import com.Jguides.spingboot.repository.UserRepository;
import com.Jguides.spingboot.service.AssetStatusService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
public class DataInitializer {

    // ---- 1) USERS -----------------------------------------------------------

    @Bean
    @Order(1)
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

    // ---- 2) ASSET STATUSES --------------------------------------------------

    @Bean
    @Order(2)
    @Transactional
    CommandLineRunner seedAssetStatuses(AssetStatusService assetStatusService) {
        return args -> {
            // Load existing status names (normalized to UPPER)
            List<AssetStatus> existing = assetStatusService.getAllAssetStatus();
            Set<String> names = existing.stream()
                    .map(s -> s.getStatusName() == null ? "" : s.getStatusName().trim().toUpperCase())
                    .collect(Collectors.toSet());

            String[] defaults = {
                    "AVAILABLE",
                    "DAMAGED",
                    "DISPOSED",
                    "IN_USE",
                    "LOST",
                    "RESERVED",
                    "UNDER_MAINTENANCE"
            };

            for (String d : defaults) {
                String normalized = d.trim().toUpperCase();
                if (!names.contains(normalized)) {
                    AssetStatus s = new AssetStatus();
                    s.setStatusName(normalized);
                    assetStatusService.saveAssetStatus(s);
                }
            }
        };
    }
}
