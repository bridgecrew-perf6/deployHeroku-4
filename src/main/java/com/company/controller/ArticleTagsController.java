package com.company.controller;

import com.company.dto.ArticleTagsDto;
import com.company.enums.ProfileRole;
import com.company.service.ArticleTagsService;
import com.company.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/tags")
public class ArticleTagsController {

    @Autowired
    private ArticleTagsService articleTagsService;

    @GetMapping("/{articleId}")
    public ResponseEntity<?> getArticleTags(@PathVariable("articleId") Integer articleId){
        return ResponseEntity.ok(articleTagsService.getArticleTags(articleId));
    }

    @PostMapping("/adm")
    public ResponseEntity<?> addTag(@RequestBody ArticleTagsDto dto,
                                    HttpServletRequest request){
        JwtUtil.getIdFromHeader(request, ProfileRole.SUPER_MODERATOR);
        return ResponseEntity.ok(articleTagsService.addTags(dto));
    }

    @DeleteMapping("/adm/{articleId}/{tagId}")
    public ResponseEntity<?> deleteTag(@PathVariable("articleId")Integer articleId,
                                       @PathVariable("tagId")Integer tagId,
                                       HttpServletRequest request){
        JwtUtil.getIdFromHeader(request,ProfileRole.SUPER_MODERATOR);
        return ResponseEntity.ok(articleTagsService.deleteTags(tagId,articleId));
    }
}
