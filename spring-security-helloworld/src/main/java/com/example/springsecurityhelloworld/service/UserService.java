package com.example.springsecurityhelloworld.service;

import com.example.springsecurityhelloworld.model.User;
import com.example.springsecurityhelloworld.repository.UserRepository;
import com.example.springsecurityhelloworld.util.CookieUtil;
import com.example.springsecurityhelloworld.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    private static final String signingKey = "metrix_kurator_kaliber";

    public void Userlogin(String userName, String password, HttpServletResponse response){

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        try{
            boolean parse=false;
            User user = (User) userRepository.findById(userName).get();
            if(!user.getUserName().isEmpty()){
                String parsCode = user.getPassword();
                parse = encoder.matches(password,parsCode);
            }
            if(parse){
                System.out.println("in tru");
                String JwtToken = JwtUtil.addToken(user,response);
                Cookie cookie = CookieUtil.create(response, "Kaliber", JwtToken, false, -1, "localhost");
                System.out.println("Token::"+JwtToken);
                System.out.println("Cookie::"+cookie);
            }
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    public void createUser(User user){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String Encoded = encoder.encode(user.getPassword());
        user.setPassword(Encoded);
        userRepository.save(user);
    }

    public String loadByUserName(HttpServletRequest request){
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
                            return user;
//                            return new UsernamePasswordAuthenticationToken(user, null, roleList);
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

    public void clearCookie (HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest){
        String userName =   loadByUserName(httpServletRequest);
        CookieUtil.clearCookie(httpServletResponse, userName);
    }

}
