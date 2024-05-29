package org.example.chatapp.services;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Map;
import java.util.UUID;

@Service
public class JwtService {
    public UUID getUserId(Jwt jwt){
        Map<String, Object> claims = jwt.getClaims();
        String sub = (String) claims.get("sub");
        return UUID.fromString(sub);
    }
}
