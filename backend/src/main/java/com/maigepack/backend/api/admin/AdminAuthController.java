package com.maigepack.backend.api.admin;

import com.maigepack.backend.model.AdminUser;
import com.maigepack.backend.repository.AdminUserRepository;
import com.maigepack.backend.security.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/auth")
public class AdminAuthController {
    private final AdminUserRepository adminUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AdminAuthController(AdminUserRepository adminUserRepository,
                                PasswordEncoder passwordEncoder,
                                JwtService jwtService) {
        this.adminUserRepository = adminUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<AdminLoginResponse> login(@RequestBody @Valid AdminLoginRequest req) {
        return adminUserRepository.findByUsername(req.getUsername())
                .filter(u -> passwordEncoder.matches(req.getPassword(), u.getPasswordHash()))
                .map(u -> new ResponseEntity<>(new AdminLoginResponse(
                        jwtService.generateToken(u.getUsername(), u.getRole()),
                        "Bearer"
                ), HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }
}

