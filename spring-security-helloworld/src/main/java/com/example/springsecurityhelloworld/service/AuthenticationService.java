package com.example.springsecurityhelloworld.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class AuthenticationService {

    private static final String signingKey = "metrix_kurator_kaliber";
    public static Authentication getAuthentication(HttpServletRequest request){
//        String token = request.getHeader("Authorization");
        if(request.getCookies()!=null) {
            String user;
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("Kaliber")) {

                    try {
                        Claims claim = Jwts.parser().setSigningKey(signingKey).parseClaimsJws(cookie.getValue())
                                .getBody();
                        user = claim.get("un", String.class);
                        if (user != null) {
                            String roles = claim.get("roles", String.class);
                            List<GrantedAuthority> roleList = AuthorityUtils.commaSeparatedStringToAuthorityList(roles);
                            System.out.println("claiming" + user);
                            return new UsernamePasswordAuthenticationToken(user, null, roleList);
                        }
                    } catch (ExpiredJwtException expiredException) {
                        System.out.println(expiredException);
                    } catch (Exception exception) {
                        System.out.println(exception);
                    }
                }

            }
        }
        return null;
    }
}
