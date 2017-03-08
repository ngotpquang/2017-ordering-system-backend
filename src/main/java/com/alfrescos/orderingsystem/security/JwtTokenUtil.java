package com.alfrescos.orderingsystem.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenUtil implements Serializable {

    private static final long serialVersionUID = -3301605591108950415L;

    static final String CLAIM_KEY_USERNAME = "sub";
    static final String CLAIM_KEY_AUDIENCE = "audience";
    static final String CLAIM_KEY_CREATED = "created";
    static final String CLAIM_KEY_EMAIL = "email";
    static final String CLAIM_KEY_URL = "url";
    static final String CLAIM_KEY_PASS = "password";


    private static final String AUDIENCE_UNKNOWN = "unknown";
    private static final String AUDIENCE_WEB = "web";
    private static final String AUDIENCE_MOBILE = "mobile";
    private static final String AUDIENCE_TABLET = "tablet";

    @Value("${security.secretKey}")
    private String secret;

    @Value("${security.expirationTime}")
    private Long expiration;

    public String getUsernameFromToken(String token) {
        String username;
        try {
            final Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    public String getEmailFromToken(String token) {
        String email;
        try {
            final Claims claims = getClaimsFromToken(token);
            email = (String) claims.get(CLAIM_KEY_EMAIL);
        } catch (Exception e) {
            email = null;
        }
        return email;
    }

    public String getUrlFromToken(String token) {
        String email;
        try {
            final Claims claims = getClaimsFromToken(token);
            email = (String) claims.get(CLAIM_KEY_URL);
        } catch (Exception e) {
            email = null;
        }
        return email;
    }

    public String getPasswordFromToken(String token) {
        String email;
        try {
            final Claims claims = getClaimsFromToken(token);
            email = (String) claims.get(CLAIM_KEY_PASS);
        } catch (Exception e) {
            email = null;
        }
        return email;
    }

    public Date getCreatedDateFromToken(String token) {
        Date created;
        try {
            final Claims claims = getClaimsFromToken(token);
            created = new Date((Long) claims.get(CLAIM_KEY_CREATED));
        } catch (Exception e) {
            created = null;
        }
        return created;
    }

    public Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            final Claims claims = getClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }

//    public String getAudienceFromToken(String token) {
//        String audience;
//        try {
//            final Claims claims = getClaimsFromToken(token);
//            audience = (String) claims.get(CLAIM_KEY_AUDIENCE);
//        } catch (Exception e) {
//            audience = null;
//        }
//        return audience;
//    }

    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + this.expiration * 1000);
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private String generateAudience() {
        return AUDIENCE_WEB;
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        claims.put(CLAIM_KEY_AUDIENCE, generateAudience());
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }

    public String generateToken(String email, String password, String urlPath){
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_EMAIL,email);
        claims.put(CLAIM_KEY_URL,urlPath);
        claims.put(CLAIM_KEY_PASS,password);
        return generateToken(claims);
    }


    String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        JwtUser user = (JwtUser) userDetails;
        final String username = getUsernameFromToken(token);
        return (
                username.equals(user.getUsername())
                        && !isTokenExpired(token));
    }

}