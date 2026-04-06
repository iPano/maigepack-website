package com.magerpack.backend.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtAuthFilterTest {

    @Mock
    private JwtService jwtService;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private FilterChain filterChain;

    private JwtAuthFilter filter;

    @BeforeEach
    void setUp() {
        filter = new JwtAuthFilter(jwtService);
        SecurityContextHolder.clearContext();
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    // -----------------------------------------------------------------------
    // Valid token — SecurityContext should be populated
    // -----------------------------------------------------------------------

    @Test
    void doFilterInternal_validBearerToken_setsAuthentication() throws Exception {
        when(request.getHeader("Authorization")).thenReturn("Bearer valid.token.here");
        when(jwtService.validateAndParse("valid.token.here"))
                .thenReturn(new JwtService.JwtClaims("alice", "admin"));

        filter.doFilterInternal(request, response, filterChain);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        assertThat(auth).isNotNull();
        assertThat(auth.getName()).isEqualTo("alice");
        assertThat(auth.getAuthorities())
                .extracting(Object::toString)
                .containsExactly("ROLE_ADMIN");

        verify(filterChain).doFilter(request, response);
    }

    @Test
    void doFilterInternal_validToken_roleIsUppercased() throws Exception {
        when(request.getHeader("Authorization")).thenReturn("Bearer some.token");
        when(jwtService.validateAndParse("some.token"))
                .thenReturn(new JwtService.JwtClaims("bob", "user"));

        filter.doFilterInternal(request, response, filterChain);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        assertThat(auth.getAuthorities())
                .extracting(Object::toString)
                .containsExactly("ROLE_USER");
    }

    // -----------------------------------------------------------------------
    // Invalid / missing token — SecurityContext should remain empty
    // -----------------------------------------------------------------------

    @Test
    void doFilterInternal_noAuthorizationHeader_doesNotSetAuthentication() throws Exception {
        when(request.getHeader("Authorization")).thenReturn(null);

        filter.doFilterInternal(request, response, filterChain);

        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
        verify(jwtService, never()).validateAndParse(any());
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void doFilterInternal_headerWithoutBearer_doesNotSetAuthentication() throws Exception {
        when(request.getHeader("Authorization")).thenReturn("Basic dXNlcjpwYXNz");

        filter.doFilterInternal(request, response, filterChain);

        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
        verify(jwtService, never()).validateAndParse(any());
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void doFilterInternal_invalidToken_doesNotSetAuthentication() throws Exception {
        when(request.getHeader("Authorization")).thenReturn("Bearer invalid.token");
        when(jwtService.validateAndParse("invalid.token")).thenReturn(null);

        filter.doFilterInternal(request, response, filterChain);

        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
        verify(filterChain).doFilter(request, response);
    }

    // -----------------------------------------------------------------------
    // Filter chain is always called
    // -----------------------------------------------------------------------

    @Test
    void doFilterInternal_alwaysCallsFilterChain_evenOnInvalidToken() throws Exception {
        when(request.getHeader("Authorization")).thenReturn("Bearer bad.token");
        when(jwtService.validateAndParse("bad.token")).thenReturn(null);

        filter.doFilterInternal(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);
    }
}
