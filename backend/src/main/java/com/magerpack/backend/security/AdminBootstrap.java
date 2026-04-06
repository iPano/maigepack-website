package com.magerpack.backend.security;

import com.magerpack.backend.model.AdminUser;
import com.magerpack.backend.repository.AdminUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminBootstrap implements CommandLineRunner {
    private final AdminUserRepository adminUserRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminBootstrap(AdminUserRepository adminUserRepository, PasswordEncoder passwordEncoder) {
        this.adminUserRepository = adminUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (adminUserRepository.count() > 0) {
            return;
        }

        // Default dev admin. Replace with proper onboarding in production.
        AdminUser u = new AdminUser();
        u.setUsername("admin");
        u.setRole("ADMIN");
        u.setPasswordHash(passwordEncoder.encode("admin"));
        adminUserRepository.save(u);
    }
}

