package com.company.controller;


import com.company.dto.AuthDto;
import com.company.dto.ProfileJwtDto;
import com.company.dto.RegDto;
import com.company.service.AuthService;
import com.company.service.EmailService;
import com.company.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/auth")
@RestController
public class AuthController {
    @Autowired
    private AuthService service;

    @PostMapping (value = "/login")
    public ResponseEntity<?> login(@RequestBody AuthDto dto){
        return ResponseEntity.ok(service.login(dto));
    }

    @PostMapping(value = "/reg")
    public ResponseEntity<?> registration(@RequestBody RegDto dto){
        return ResponseEntity.ok(service.registration(dto));
    }

    @GetMapping(value = "/verification/{jwt}")
    public ResponseEntity<?> verification(@PathVariable("jwt") String jwt){
        return ResponseEntity.ok(service.verification(jwt));
    }
}

