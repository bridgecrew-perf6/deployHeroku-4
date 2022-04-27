package com.company.controller;

import com.company.enums.ProfileRole;
import com.company.service.ProfileImageService;
import com.company.service.ProfileService;
import com.company.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/profile-image")
public class ProfileImageController {
    @Autowired
    private ProfileImageService service;

    @PostMapping("/upload")
    public ResponseEntity<?> upload(MultipartFile file, HttpServletRequest request){
        Integer id = JwtUtil.getIdFromHeader(request);
        return ResponseEntity.ok(service.upload(id,file));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete( HttpServletRequest request){
        Integer id = JwtUtil.getIdFromHeader(request);
        return ResponseEntity.ok(service.delete(id));
    }


}
