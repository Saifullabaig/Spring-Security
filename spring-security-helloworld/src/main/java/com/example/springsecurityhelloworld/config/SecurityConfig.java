package com.example.springsecurityhelloworld.config;

import com.example.springsecurityhelloworld.filter.JwtFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity https) throws Exception{
        https.csrf().disable().authorizeRequests().and().antMatcher("/**").authorizeRequests().anyRequest()
                .authenticated().and().addFilterAfter(new JwtFilter(), BasicAuthenticationFilter.class)
                .authorizeRequests();
    }

    @Override
    public void configure(WebSecurity webSec){
        webSec.ignoring().antMatchers("/user","/login","/h2-console/**","/logout");
    }
}
