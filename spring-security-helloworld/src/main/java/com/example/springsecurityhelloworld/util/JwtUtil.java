package com.example.springsecurityhelloworld.util;

import com.example.springsecurityhelloworld.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Component
public class JwtUtil {
    static final long ExpirationTime = 3600000;
    private static final String signingKey ="metrix_kurator_kaliber";
//    private static final String PREFIX = "Bearer";

    public static String addToken(User user, HttpServletResponse response){
        System.out.println("jwt");
        Claims claims = Jwts.claims();
        claims.put("un", user.getUserName());
        claims.put("roles", user.getRole());
        System.out.println("claim"+ claims);
        String jwtToken = Jwts.builder().setClaims(claims).setExpiration(new Date(System.currentTimeMillis()+ExpirationTime))
                .signWith(SignatureAlgorithm.HS512, signingKey).compact();
//        response.addHeader("Authorization", PREFIX + " "+jwtToken);
//        response.addHeader("Access-Control-Expose-Headers", "Authorization");
//        System.out.println("jasdasda"+jwtToken);

        return jwtToken;
    }
}
