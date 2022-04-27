package com.company.controller;

import com.company.dto.LikeDto;
import com.company.dto.ProfileJwtDto;
import com.company.service.LikeService;
import com.company.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/like")
public class LikeController {

    @Autowired
    private LikeService service;

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody LikeDto dto,
                                    HttpServletRequest request) {
        Integer pId = JwtUtil.getIdFromHeader(request);
        return ResponseEntity.ok(service.create(pId, dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id,
                                    @RequestBody LikeDto dto,
                                    HttpServletRequest request) {
        Integer pId = JwtUtil.getIdFromHeader(request);
        return ResponseEntity.ok(service.update(id, dto, pId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id,
                                    HttpServletRequest request) {
        ProfileJwtDto profileJwt = JwtUtil.getProfileFromHeader(request);
        return ResponseEntity.ok(service.delete(id, profileJwt.getId(), profileJwt.getRole()));
    }

    @GetMapping("/article/{id}")
    public ResponseEntity<?> findAllByArticleId(@PathVariable("id") Integer articleId,
                                                @RequestParam(value = "page", defaultValue = "0") int page,
                                                @RequestParam(value = "size", defaultValue = "30") int size) {
        return ResponseEntity.ok(service.listByArticleId(articleId, page, size));
    }

    @GetMapping("/profile/{id}")
    public ResponseEntity<?> findAllByProfileId(@PathVariable("id") Integer profileId,
                                                @RequestParam(value = "page", defaultValue = "0") int page,
                                                @RequestParam(value = "size", defaultValue = "30") int size) {
        return ResponseEntity.ok(service.listByprofileId(profileId, page, size));
    }

    @GetMapping("")
    public ResponseEntity<?> findAll(@RequestParam(value = "page", defaultValue = "0") int page,
                                     @RequestParam(value = "size", defaultValue = "30") int size) {
        return ResponseEntity.ok(service.getList( page, size));
    }

    @GetMapping("/profile/{articleId}")
    public ResponseEntity<?> checkLike(@PathVariable("articleId") Integer articleId,
                                       HttpServletRequest request){
        Integer pId = JwtUtil.getIdFromHeader(request);
        return ResponseEntity.ok(service.checkLike(articleId,pId));
    }
}
