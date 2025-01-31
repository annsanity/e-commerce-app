package com.ecommerce.projectapp.config;

import java.net.Authenticator;

import javax.crypto.SecretKey;

@Service
public class JwtProvider {

    private SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY);

    public String generateToken(Authenticatoion auth) {
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        String roles = populateAuthorities(authorities);

        String jwt = Jwts.builder()
        .setIssuedAt(new Date())
        .setExpiration(new Date(new Date().getTime + 86400000))
        .claim("email", auth.getName())
        .claim("authorities", roles)
        .signWith(key)
        .compact();

        return jwt;

    }

    public String getEmailFromJwtToken(String jwt) {

        // bearer 
        jwt = jwt.substring(7);

        Claims claims = Jwts.parseBuilder()
                        .setSigning(key)
                        .build()
                        .parseClaimsJws(jwt)
                        .getBody();

        String email=String.valueOf(claims.get("email"));
		
        return email;
    }

    public String populateAuthorities(Collection<? extends GrantedAuthority> collection>) {
        Set<String> auths = new HashSet<>();

        for(GrantedAuthority authority : collection){
            auths.add(authority.getAuthority);
        }

        return String.join(",",auths);
    }
}
