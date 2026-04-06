package com.magerpack.backend.api.admin;

import com.magerpack.backend.model.AdminUser;
import com.magerpack.backend.repository.AdminUserRepository;
import com.magerpack.backend.security.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminAuthControllerTest {

    @Mock
    private AdminUserRepository adminUserRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtService jwtService;

    private AdminAuthController controller;

    @BeforeEach
    void setUp() {
        controller = new AdminAuthController(adminUserRepository, passwordEncoder, jwtService);
    }

    // -----------------------------------------------------------------------
    // Successful login
    // -----------------------------------------------------------------------

    @Test
    void login_validCredentials_returns200WithToken() {
        AdminUser user = buildAdminUser("alice", "$2a$hash", "ADMIN");
        when(adminUserRepository.findByUsername("alice")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("secret", "$2a$hash")).thenReturn(true);
        when(jwtService.generateToken("alice", "ADMIN")).thenReturn("jwt.token.value");

        ResponseEntity<AdminLoginResponse> response = controller.login(buildRequest("alice", "secret"));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getToken()).isEqualTo("jwt.token.value");
        assertThat(response.getBody().getTokenType()).isEqualTo("Bearer");
    }

    @Test
    void login_validCredentials_callsJwtServiceWithUsernameAndRole() {
        AdminUser user = buildAdminUser("alice", "$2a$hash", "ADMIN");
        when(adminUserRepository.findByUsername("alice")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("secret", "$2a$hash")).thenReturn(true);
        when(jwtService.generateToken("alice", "ADMIN")).thenReturn("token");

        controller.login(buildRequest("alice", "secret"));

        verify(jwtService).generateToken("alice", "ADMIN");
    }

    // -----------------------------------------------------------------------
    // Failed login — wrong password
    // -----------------------------------------------------------------------

    @Test
    void login_wrongPassword_returns401() {
        AdminUser user = buildAdminUser("alice", "$2a$hash", "ADMIN");
        when(adminUserRepository.findByUsername("alice")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrong", "$2a$hash")).thenReturn(false);

        ResponseEntity<AdminLoginResponse> response = controller.login(buildRequest("alice", "wrong"));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(response.getBody()).isNull();
    }

    @Test
    void login_wrongPassword_doesNotGenerateToken() {
        AdminUser user = buildAdminUser("alice", "$2a$hash", "ADMIN");
        when(adminUserRepository.findByUsername("alice")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(any(), any())).thenReturn(false);

        controller.login(buildRequest("alice", "wrong"));

        verify(jwtService, never()).generateToken(any(), any());
    }

    // -----------------------------------------------------------------------
    // Failed login — user not found
    // -----------------------------------------------------------------------

    @Test
    void login_unknownUser_returns401() {
        when(adminUserRepository.findByUsername("ghost")).thenReturn(Optional.empty());

        ResponseEntity<AdminLoginResponse> response = controller.login(buildRequest("ghost", "pass"));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    void login_unknownUser_doesNotCheckPassword() {
        when(adminUserRepository.findByUsername("ghost")).thenReturn(Optional.empty());

        controller.login(buildRequest("ghost", "pass"));

        verify(passwordEncoder, never()).matches(any(), any());
    }

    // -----------------------------------------------------------------------
    // Helpers
    // -----------------------------------------------------------------------

    private AdminLoginRequest buildRequest(String username, String password) {
        AdminLoginRequest req = new AdminLoginRequest();
        req.setUsername(username);
        req.setPassword(password);
        return req;
    }

    private AdminUser buildAdminUser(String username, String hash, String role) {
        AdminUser u = new AdminUser();
        u.setUsername(username);
        u.setPasswordHash(hash);
        u.setRole(role);
        return u;
    }
}
