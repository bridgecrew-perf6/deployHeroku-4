package com.company.controller;

import com.company.dto.RegionDto;
import com.company.enums.LangEnum;
import com.company.enums.ProfileRole;
import com.company.service.RegionService;
import com.company.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/region")
public class RegionController {

    @Autowired
    private RegionService service;

    @PostMapping("/adm")
    public ResponseEntity<?> create(@RequestBody RegionDto dto ,
                                    HttpServletRequest request){
        Integer pId = JwtUtil.getIdFromHeader(request,ProfileRole.ADMIN);
        return  ResponseEntity.ok(service.create(dto, pId));
    }

    @GetMapping("/adm")
    public ResponseEntity<?> getList(HttpServletRequest request){
        Integer pid = JwtUtil.getIdFromHeader(request);
        return ResponseEntity.ok(service.getList(pid));
    }

    @GetMapping("/public/{lang}")
    public ResponseEntity<?> getList(@PathVariable("lang") LangEnum lang) {
        return ResponseEntity.ok(service.getList(lang));
    }

    @PutMapping("/adm/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id, @RequestBody RegionDto dto,
                                    HttpServletRequest request){
        Integer pid = JwtUtil.getIdFromHeader(request);
        return ResponseEntity.ok(service.update(id,dto,pid));
    }

    @DeleteMapping("/adm/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id,
                                    HttpServletRequest request){
        Integer pId = JwtUtil.getIdFromHeader(request,ProfileRole.ADMIN);
        return ResponseEntity.ok(service.delete(id,pId));
    }




}
