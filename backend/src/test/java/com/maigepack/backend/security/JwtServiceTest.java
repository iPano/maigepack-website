package com.maigepack.backend.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Base64;

import static org.assertj.core.api.Assertions.*;

class JwtServiceTest {

    private static final String SECRET = "test-secret-key-for-unit-tests";
    private static final String ISSUER = "test-issuer";

    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService(new ObjectMapper(), SECRET, ISSUER);
    }

    // -----------------------------------------------------------------------
    // generateToken
    // -----------------------------------------------------------------------

    @Test
    void generateToken_returnsThreePartToken() {
        String token = jwtService.generateToken("admin", "ADMIN");
        String[] parts = token.split("\\.");
        assertThat(parts).hasSize(3);
    }

    @Test
    void generateToken_tokenIsNotBlank() {
        String token = jwtService.generateToken("user", "USER");
        assertThat(token).isNotBlank();
    }

    @Test
    void generateToken_differentUsersProduceDifferentTokens() {
        String t1 = jwtService.generateToken("alice", "ADMIN");
        String t2 = jwtService.generateToken("bob", "ADMIN");
        assertThat(t1).isNotEqualTo(t2);
    }

    @Test
    void generateToken_differentRolesProduceDifferentTokens() {
        String t1 = jwtService.generateToken("alice", "ADMIN");
        String t2 = jwtService.generateToken("alice", "USER");
        assertThat(t1).isNotEqualTo(t2);
    }

    // -----------------------------------------------------------------------
    // validateAndParse — happy path
    // -----------------------------------------------------------------------

    @Test
    void validateAndParse_roundTrip_returnsCorrectClaims() {
        String token = jwtService.generateToken("alice", "ADMIN");
        JwtService.JwtClaims claims = jwtService.validateAndParse(token);
        assertThat(claims).isNotNull();
        assertThat(claims.username()).isEqualTo("alice");
        assertThat(claims.role()).isEqualTo("ADMIN");
    }

    @Test
    void validateAndParse_roundTrip_preservesUsernameWithSpecialCharacters() {
        String token = jwtService.generateToken("user@example.com", "USER");
        JwtService.JwtClaims claims = jwtService.validateAndParse(token);
        assertThat(claims).isNotNull();
        assertThat(claims.username()).isEqualTo("user@example.com");
    }

    // -----------------------------------------------------------------------
    // validateAndParse — null / blank / malformed input
    // -----------------------------------------------------------------------

    @Test
    void validateAndParse_nullToken_returnsNull() {
        assertThat(jwtService.validateAndParse(null)).isNull();
    }

    @Test
    void validateAndParse_emptyToken_returnsNull() {
        assertThat(jwtService.validateAndParse("")).isNull();
    }

    @Test
    void validateAndParse_blankToken_returnsNull() {
        assertThat(jwtService.validateAndParse("   ")).isNull();
    }

    @Test
    void validateAndParse_tokenWithTwoPartOnly_returnsNull() {
        assertThat(jwtService.validateAndParse("header.payload")).isNull();
    }

    @Test
    void validateAndParse_tokenWithFourParts_returnsNull() {
        assertThat(jwtService.validateAndParse("a.b.c.d")).isNull();
    }

    @Test
    void validateAndParse_randomString_returnsNull() {
        assertThat(jwtService.validateAndParse("not_a_jwt_token")).isNull();
    }

    // -----------------------------------------------------------------------
    // validateAndParse — tampered signature
    // -----------------------------------------------------------------------

    @Test
    void validateAndParse_tamperedSignature_returnsNull() {
        String token = jwtService.generateToken("alice", "ADMIN");
        // Replace the signature part with a valid-length but incorrect base64url string.
        // A real HMAC-SHA256 signature is 32 bytes → 43 base64url chars (no padding).
        int lastDot = token.lastIndexOf('.');
        String wrongSig = "A".repeat(43); // 43 'A's is valid base64url but wrong bytes
        String tampered = token.substring(0, lastDot + 1) + wrongSig;
        assertThat(jwtService.validateAndParse(tampered)).isNull();
    }

    @Test
    void validateAndParse_tokenSignedWithDifferentSecret_returnsNull() {
        JwtService otherService = new JwtService(new ObjectMapper(), "completely-different-secret", ISSUER);
        String foreignToken = otherService.generateToken("alice", "ADMIN");
        assertThat(jwtService.validateAndParse(foreignToken)).isNull();
    }

    // -----------------------------------------------------------------------
    // validateAndParse — tampered payload
    // -----------------------------------------------------------------------

    @Test
    void validateAndParse_tamperedPayload_returnsNull() {
        String token = jwtService.generateToken("alice", "ADMIN");
        String[] parts = token.split("\\.");

        // Replace username in the payload with "hacker"
        String payloadJson = new String(Base64.getUrlDecoder().decode(parts[1]));
        String tamperedJson = payloadJson.replace("\"alice\"", "\"hacker\"");
        String tamperedPayload = Base64.getUrlEncoder().withoutPadding()
                .encodeToString(tamperedJson.getBytes());

        String tamperedToken = parts[0] + "." + tamperedPayload + "." + parts[2];
        assertThat(jwtService.validateAndParse(tamperedToken)).isNull();
    }

    // -----------------------------------------------------------------------
    // validateAndParse — wrong issuer
    // -----------------------------------------------------------------------

    @Test
    void validateAndParse_wrongIssuer_returnsNull() {
        JwtService wrongIssuerService = new JwtService(new ObjectMapper(), SECRET, "wrong-issuer");
        String token = wrongIssuerService.generateToken("alice", "ADMIN");
        // validating service expects ISSUER, token has "wrong-issuer"
        assertThat(jwtService.validateAndParse(token)).isNull();
    }

    // -----------------------------------------------------------------------
    // validateAndParse — expired token (crafted manually)
    // -----------------------------------------------------------------------

    @Test
    void validateAndParse_expiredToken_returnsNull() throws Exception {
        // Build a token with exp set in the past
        ObjectMapper mapper = new ObjectMapper();

        String headerJson = "{\"alg\":\"HS256\",\"typ\":\"JWT\"}";
        String headerB64 = Base64.getUrlEncoder().withoutPadding()
                .encodeToString(headerJson.getBytes());

        long pastExp = (System.currentTimeMillis() / 1000) - 3600; // 1 hour ago
        String payloadJson = String.format(
                "{\"sub\":\"alice\",\"role\":\"ADMIN\",\"iss\":\"%s\",\"iat\":%d,\"exp\":%d}",
                ISSUER, pastExp - 7200, pastExp);
        String payloadB64 = Base64.getUrlEncoder().withoutPadding()
                .encodeToString(payloadJson.getBytes());

        // Sign with the correct secret so only expiry is the failure condition
        String data = headerB64 + "." + payloadB64;
        javax.crypto.Mac mac = javax.crypto.Mac.getInstance("HmacSHA256");
        mac.init(new javax.crypto.spec.SecretKeySpec(SECRET.getBytes(), "HmacSHA256"));
        byte[] sig = mac.doFinal(data.getBytes());
        String sigB64 = Base64.getUrlEncoder().withoutPadding().encodeToString(sig);

        String expiredToken = data + "." + sigB64;
        assertThat(jwtService.validateAndParse(expiredToken)).isNull();
    }
}
