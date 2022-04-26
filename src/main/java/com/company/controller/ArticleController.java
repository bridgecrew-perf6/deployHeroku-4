package com.company.controller;

import com.company.dto.ArticleDto;
import com.company.enums.ProfileRole;
import com.company.service.ArticleService;
import com.company.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService service;

    @PostMapping("/adm")
    public ResponseEntity<?> create(@RequestBody ArticleDto dto,
                                    HttpServletRequest request) {
        Integer id = JwtUtil.getIdFromHeader(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(service.create(dto, id));
    }

    @GetMapping("/public/list")
    public ResponseEntity<?> listByLang(@RequestParam(value = "page", defaultValue = "0") int page,
                                        @RequestParam(value = "size", defaultValue = "5") int size) {
        return ResponseEntity.ok(service.list(page, size));
    }

    @PutMapping("/adm/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id,
                                    @RequestBody ArticleDto dto,
                                    HttpServletRequest request) {
        Integer pId = JwtUtil.getIdFromHeader(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(service.update(id, dto, pId));
    }

    @DeleteMapping("/adm/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id, HttpServletRequest request) {
        JwtUtil.getIdFromHeader(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(service.delete(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping("/lastAdded")
    public ResponseEntity<?> lastAdded4() {
        return ResponseEntity.ok(service.last4());
    }

    @GetMapping("/lastByRegion/{id}")
    public ResponseEntity<?> last4ByRegion(@PathVariable("id") Integer regionId) {
        return ResponseEntity.ok(service.last4ByRegion(regionId));
    }

    @GetMapping("/lastByCategory/{id}")
    public ResponseEntity<?> last4ByCategory(@PathVariable("id") Integer categoryId) {
        return ResponseEntity.ok(service.last4ByCategory(categoryId));
    }

    @GetMapping("/getByType/{id}")
    public ResponseEntity<?> getByType(@RequestParam(value = "page", defaultValue = "0") int page,
                                       @RequestParam(value = "size", defaultValue = "10") int size,
                                       @PathVariable("id") Integer typeId) {
        return ResponseEntity.ok(service.getArticleByType(typeId, page, size));
    }

    @GetMapping("/getByCategory/{id}")
    public ResponseEntity<?> getByCategory(@RequestParam(value = "page", defaultValue = "0") int page,
                                           @RequestParam(value = "size", defaultValue = "10") int size,
                                           @PathVariable("id") Integer categoryId) {
        return ResponseEntity.ok(service.getArticleByCategory(categoryId, page, size));
    }

    @GetMapping("/getByRegion/{id}")
    public ResponseEntity<?> getByRegion(@RequestParam(value = "page", defaultValue = "0") int page,
                                         @RequestParam(value = "size", defaultValue = "10") int size,
                                         @PathVariable("id") Integer regId) {
        return ResponseEntity.ok(service.getArticleByRegion(regId, page, size));
    }

    @GetMapping("/getByProfile/{id}")
    public ResponseEntity<?> getByProfile(@RequestParam(value = "page", defaultValue = "0") int page,
                                          @RequestParam(value = "size", defaultValue = "10") int size,
                                          @PathVariable("id") Integer pId) {
        return ResponseEntity.ok(service.getProfileArticle(pId, page, size));
    }



    // 2. Article (region,type,attach,viewCount)
    // getProfileArticle + pagination,
    // list
    // getArticleListBy Region
    // getArticleListBy Category
    // getArticleListBy Type
    // lastAdded4 articles
    // lastAdded 4 articles by region
    // lastAdded 4 articles by category


}
