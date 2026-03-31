package com.maigepack.backend.security;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {
    private final ObjectMapper objectMapper;
    private final String secret;
    private final String issuer;

    public JwtService(ObjectMapper objectMapper,
                       @Value("${app.auth.jwtSecret:change_me}") String secret,
                       @Value("${app.auth.jwtIssuer:maigepack}") String issuer) {
        this.objectMapper = objectMapper;
        this.secret = secret;
        this.issuer = issuer;
    }

    public String generateToken(String username, String role) {
        Instant now = Instant.now();
        long iat = now.getEpochSecond();
        long exp = now.plusSeconds(86400).getEpochSecond(); // 24h

        Map<String, Object> header = new HashMap<>();
        header.put("alg", "HS256");
        header.put("typ", "JWT");

        Map<String, Object> payload = new HashMap<>();
        payload.put("sub", username);
        payload.put("role", role);
        payload.put("iss", issuer);
        payload.put("iat", iat);
        payload.put("exp", exp);

        String headerB64 = base64UrlEncode(json(header).getBytes(StandardCharsets.UTF_8));
        String payloadB64 = base64UrlEncode(json(payload).getBytes(StandardCharsets.UTF_8));
        String data = headerB64 + "." + payloadB64;

        byte[] signature = hmacSha256(data.getBytes(StandardCharsets.UTF_8), secret.getBytes(StandardCharsets.UTF_8));
        String sigB64 = base64UrlEncode(signature);
        return data + "." + sigB64;
    }

    public JwtClaims validateAndParse(String token) {
        if (token == null || token.isBlank()) {
            return null;
        }

        String[] parts = token.split("\\.");
        if (parts.length != 3) {
            return null;
        }

        String headerB64 = parts[0];
        String payloadB64 = parts[1];
        String sigB64 = parts[2];

        String data = headerB64 + "." + payloadB64;
        byte[] expectedSig = hmacSha256(data.getBytes(StandardCharsets.UTF_8), secret.getBytes(StandardCharsets.UTF_8));
        byte[] actualSig = Base64.getUrlDecoder().decode(sigB64);
        if (!constantTimeEquals(expectedSig, actualSig)) {
            return null;
        }

        String payloadJson = new String(Base64.getUrlDecoder().decode(payloadB64), StandardCharsets.UTF_8);
        Map<String, Object> payload = parseJson(payloadJson);

        Object expObj = payload.get("exp");
        Object issObj = payload.get("iss");
        if (expObj == null || issObj == null) {
            return null;
        }

        long exp = ((Number) expObj).longValue();
        String iss = String.valueOf(issObj);
        if (!issuer.equals(iss)) {
            return null;
        }
        if (Instant.now().getEpochSecond() >= exp) {
            return null;
        }

        String username = String.valueOf(payload.get("sub"));
        String role = String.valueOf(payload.get("role"));
        return new JwtClaims(username, role);
    }

    private String json(Map<String, Object> map) {
        try {
            return objectMapper.writeValueAsString(map);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Map<String, Object> parseJson(String json) {
        try {
            return objectMapper.readValue(json, new TypeReference<>() {});
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private byte[] hmacSha256(byte[] data, byte[] key) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(key, "HmacSHA256"));
            return mac.doFinal(data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private boolean constantTimeEquals(byte[] a, byte[] b) {
        if (a == null || b == null || a.length != b.length) {
            return false;
        }
        int result = 0;
        for (int i = 0; i < a.length; i++) {
            result |= a[i] ^ b[i];
        }
        return result == 0;
    }

    private String base64UrlEncode(byte[] bytes) {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    public record JwtClaims(String username, String role) {
    }
}

