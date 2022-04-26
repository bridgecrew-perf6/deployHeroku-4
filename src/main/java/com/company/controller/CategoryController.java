package com.company.controller;

import com.company.dto.CategoryDto;
import com.company.enums.LangEnum;
import com.company.enums.ProfileRole;
import com.company.service.CategoryService;
import com.company.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/adm")
    public ResponseEntity<?> create(CategoryDto dto,
                                    HttpServletRequest request){
        Integer pId = JwtUtil.getIdFromHeader(request, ProfileRole.ADMIN);

        return ResponseEntity.ok(categoryService.create(dto,pId));

    }

    @GetMapping("/public/{lang}")
    public ResponseEntity<?> getList(@PathVariable("lang") LangEnum lang){
        return ResponseEntity.ok(categoryService.getList(lang));
    }

    @GetMapping("/adm")
    public ResponseEntity<?> getList(HttpServletRequest request){
        Integer pId = JwtUtil.getIdFromHeader(request,ProfileRole.ADMIN);
        return ResponseEntity.ok(categoryService.getList(pId));

    }

    @DeleteMapping("/adm/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id, HttpServletRequest request){
        Integer pId = JwtUtil.getIdFromHeader(request,ProfileRole.ADMIN);

        return ResponseEntity.ok(categoryService.delete(id,pId));
    }

    @PutMapping("/adm/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id,
                                    @RequestBody CategoryDto dto,
                                    HttpServletRequest request){
        Integer pId = JwtUtil.getIdFromHeader(request,ProfileRole.ADMIN);
        return ResponseEntity.ok(categoryService.update(id,dto,pId));

    }

    @GetMapping("/adm/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Integer id,
                                     HttpServletRequest request){

        Integer pId = JwtUtil.getIdFromHeader(request,ProfileRole.ADMIN);

        return ResponseEntity.ok(categoryService.getById(id,pId));
    }
}
