package com.maigepack.backend.security;

import com.maigepack.backend.model.AdminUser;
import com.maigepack.backend.repository.AdminUserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminBootstrap implements CommandLineRunner {
    private final AdminUserRepository adminUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.admin.username:admin}")
    private String adminUsername;

    @Value("${app.admin.password:admin}")
    private String adminPassword;

    public AdminBootstrap(AdminUserRepository adminUserRepository, PasswordEncoder passwordEncoder) {
        this.adminUserRepository = adminUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (adminUserRepository.count() > 0) {
            return;
        }

        AdminUser u = new AdminUser();
        u.setUsername(adminUsername);
        u.setRole("ADMIN");
        u.setPasswordHash(passwordEncoder.encode(adminPassword));
        adminUserRepository.save(u);
    }
}

