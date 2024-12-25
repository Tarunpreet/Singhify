package com.Singhify.Singhify.Utilities;

import com.Singhify.Singhify.Enum.Roles;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;

@Component
public class JWTUtil {
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 10; // 10 hours

    public String extractToken(String authHeader) {
        return authHeader.substring(7);
    }

    public Boolean validateToken(String token) {
        return !extractExpiry(token).before(new Date());
    }

    private Date extractExpiry(String token) {
        return extractAllClaims(token).getExpiration();
    }

    private Claims extractAllClaims(String token) {
        return   Jwts.parserBuilder()
                .setSigningKey(getkey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractUserName(String token) {
       return extractAllClaims(token).getSubject();
    }


    public String generateToken(String userName, Set<Roles> userRoles,Map<String,Object> claim) {

        return Jwts.builder()
                .setSubject(userName)
                .addClaims(claim)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getkey())
                .compact();

    }

    private Key getkey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode("ab3a9823791c16a4deb53a85b75022050ec6ae8b38ef5ef45d30e8abf611686779e92f2f3de5e898f688f0310f18d3b8f2c2f63348e837f922cfd362ff1a78bc0dacf8de7e6562bab967deb53ce311204e28279da21301aff781f0cfc8472a45f377ddbdd2fd7fbcf5efd6cb853d7f9a808c716a269630f6a025038f09a8bf9b7f277791ba2583a79576d3899dd8851ad68e9387d3160868cc7c3e6814102959a3435d249592cc9c2945df6a6b79b5d721da9d011fcd7e1381a8340087d7273925d2adcea8dc5432c7f10cee937da02509343605afd0644fa92cedc2de21a18691a571b3fba65692eb4caaf81d6af48866cca6d2fe83a2ba2ad868f460d801a8"));
    }
}
