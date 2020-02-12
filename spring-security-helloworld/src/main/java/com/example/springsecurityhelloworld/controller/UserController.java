package com.example.springsecurityhelloworld.controller;

import com.example.springsecurityhelloworld.model.User;
import com.example.springsecurityhelloworld.service.UserService;
import com.example.springsecurityhelloworld.util.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/login")
    public ResponseEntity<?> userLogin(@RequestParam String userName, String password, HttpServletResponse response){
        userService.Userlogin(userName,password,response);
        return new ResponseEntity<>("Succesfully JWt generated", HttpStatus.OK);
    }

    @GetMapping("/hello")
    public String Hello(){
        return "Hello";
    }
    @PostMapping("/user")
    public void createUser(@RequestBody User user){
        userService.createUser(user);
    }

    @GetMapping("/logout")
    public void logout(HttpServletResponse response){
        CookieUtil.clearCookie(response, "Kaliber");
    }
}
