package com.company.controller;


import com.company.dto.ProfileDto;
import com.company.enums.ProfileRole;
import com.company.service.ProfileService;
import com.company.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;



@RequestMapping("/profile")
@RestController
public class ProfileController {
    @Autowired
    private ProfileService service;

    @PostMapping("/adm")
    public ResponseEntity<?> create(@RequestBody ProfileDto dto,
                                    HttpServletRequest request){
        Integer pId = JwtUtil.getIdFromHeader(request,ProfileRole.ADMIN);
        return ResponseEntity.ok(service.create(dto,pId));
    }

    @GetMapping("/adm")
    public ResponseEntity<?> get(HttpServletRequest request){
        Integer pId = JwtUtil.getIdFromHeader(request,ProfileRole.ADMIN);
        return ResponseEntity.ok(service.getList(pId));
    }

    @GetMapping("/adm/list")
    public ResponseEntity<?> get(@RequestParam(value = "page",defaultValue = "0") int page,
                                @RequestParam(value = "size",defaultValue = "10") int size,
                                HttpServletRequest request) {
        Integer pId = JwtUtil.getIdFromHeader(request,ProfileRole.ADMIN);
        return  ResponseEntity.ok(service.getList(PageRequest.of(page, size),pId));
    }

    @PutMapping("/adm")
    public ResponseEntity<?> update(Integer id,ProfileDto dto,
                                    HttpServletRequest request){
        Integer pId = JwtUtil.getIdFromHeader(request,ProfileRole.ADMIN);

        return ResponseEntity.ok(service.update(id,dto,pId));
    }

    @DeleteMapping("/adm")
    public ResponseEntity<?> delete(Integer id,HttpServletRequest request){
        Integer pId = JwtUtil.getIdFromHeader(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(service.delete(id,pId));
    }




}
