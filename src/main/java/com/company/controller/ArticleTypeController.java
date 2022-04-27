package com.company.controller;

import com.company.dto.ArticleTypeDto;
import com.company.enums.LangEnum;
import com.company.enums.ProfileRole;
import com.company.service.ArticleTypeService;
import com.company.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/articleType")
@RequiredArgsConstructor
public class ArticleTypeController {

    private final ArticleTypeService service;

    @GetMapping("/adm")
    public ResponseEntity<?> getList(HttpServletRequest request){
        Integer pId = JwtUtil.getIdFromHeader(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(service.list(pId));

    }

    @PostMapping("/adm")
    public ResponseEntity<?> create(@RequestBody ArticleTypeDto dto,
                                    HttpServletRequest request){
        Integer pId = JwtUtil.getIdFromHeader(request,ProfileRole.ADMIN);
        return  ResponseEntity.ok(service.create(dto,pId));
    }

    @GetMapping("/public/{lang}")
    public ResponseEntity<?> getList(@PathVariable("lang") LangEnum lang){
        return ResponseEntity.ok(service.list(lang));
    }

    @PutMapping ("/adm/{id}")
    public ResponseEntity<?> update(@PathVariable("id")Integer id, @RequestBody ArticleTypeDto dto,
                                    HttpServletRequest request){
        Integer pId = JwtUtil.getIdFromHeader(request,ProfileRole.ADMIN);
        return ResponseEntity.ok(service.update(id,dto,pId));
    }

    @DeleteMapping("/adm/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id,
                                    HttpServletRequest request){
        Integer pId = JwtUtil.getIdFromHeader(request,ProfileRole.ADMIN);

        return ResponseEntity.ok(service.delete(id,pId));
    }



}
