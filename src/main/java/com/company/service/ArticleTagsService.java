package com.company.service;

import com.company.dto.ArticleTagsDto;
import com.company.entity.ArticleTagsEntity;
import com.company.exception.ItemNotFoundException;
import com.company.repository.ArticleTagsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class ArticleTagsService {

    @Autowired
    private ArticleTagsRepository tagsRepository;

    public ArticleTagsDto addTags(ArticleTagsDto dto){
        ArticleTagsEntity entity = new ArticleTagsEntity();
        entity.setTagId(dto.getTagId());
        entity.setArticleId(dto.getArticleId());
        tagsRepository.save(entity);

        return toDto(entity);
    }

    public Boolean deleteTags(Integer tagId, Integer articleId){

       tagsRepository.findByArticleIdAndTagId(articleId, tagId).orElseThrow(()->{
           throw new ItemNotFoundException("Not found");
       });

        tagsRepository.deleteByArticleIdAndTagId(articleId,tagId);
        return true;
    }

    public List<ArticleTagsDto> getArticleTags(Integer articleId){
        List<ArticleTagsDto> list = new LinkedList<>();
        tagsRepository.findAllByArticleId(articleId).forEach(entity -> {
            list.add(toDto(entity));
        });
        return list;
    }




    public ArticleTagsDto toDto(ArticleTagsEntity entity){
        ArticleTagsDto dto = new ArticleTagsDto();
        dto.setId(entity.getId());
        dto.setCreateDate(entity.getCreateDate());
        dto.setArticleId(entity.getArticleId());
        dto.setTagId(entity.getTagId());
        return dto;
    }


}
