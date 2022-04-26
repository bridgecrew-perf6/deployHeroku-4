package com.company.service;

import com.company.dto.ComentDto;
import com.company.entity.ArticleEntity;
import com.company.entity.ComentEntity;
import com.company.enums.ProfileRole;
import com.company.exception.AppForbiddenexception;
import com.company.exception.ItemNotFoundException;
import com.company.repository.CommentRepository;
import com.company.validation.CommentValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentRepository repository;
    @Autowired
    private ArticleService articleService;

    public ComentDto create(Integer id, ComentDto dto) {
        ArticleEntity article = articleService.get(dto.getArticle_id());
        CommentValidation.isValid(dto);
        ComentEntity entity = new ComentEntity();
        entity.setArticle_id(dto.getArticle_id());
        entity.setProfileId(id);
        entity.setTitle(dto.getTitle());
        repository.save(entity);

        dto.setId(entity.getId());
        dto.setCreateDate(entity.getCreateDate());
        return dto;
    }

    public Boolean delete(Integer id, Integer pId, ProfileRole role){
        ComentEntity entity = getById(id);
        if(!entity.getProfileId().equals(pId) || !role.equals(ProfileRole.ADMIN)){
            throw new AppForbiddenexception("Not Access");
        }
        repository.deleteById(id);
        return true;
    }
    public ComentEntity getById(Integer id){
      return    repository.findById(id).orElseThrow(()->{
            throw new ItemNotFoundException("Comment not found");
        });
    }

    public ComentDto update(Integer id, ComentDto dto,Integer pId){
        ComentEntity entity = getById(id);
        if(!entity.getProfileId().equals(pId)){
            throw new AppForbiddenexception("Not Access");
        }

        CommentValidation.isValid(dto);
        entity.setTitle(dto.getTitle());
        repository.save(entity);
        return dto;

    }

    public List<ComentDto> getList(int page,int size){
        Pageable pageable = PageRequest.of(page,size,
                Sort.by(Sort.Direction.DESC,"createDate"));
        List<ComentDto> list = new LinkedList<>();
        repository.findAll(pageable).forEach(comentEntity -> {
            list.add(toDto(comentEntity));
        });
        return list;
    }
    public PageImpl<ComentDto> listByArticleId(Integer articleId,int page,int size){
        Pageable pageable = PageRequest.of(page,size,
                Sort.by(Sort.Direction.DESC,"createDate"));
        Page<ComentEntity> pageList = repository.findAllByArticleId(articleId, pageable);

        List<ComentDto> dtoList = new LinkedList<>();
        for (ComentEntity entity : pageList.getContent()) {
            dtoList.add(toDto(entity));

        }
        return new PageImpl<>(dtoList,pageable,pageList.getTotalElements());
    }
    public PageImpl<ComentDto> listByprofileId(Integer pId,int page,int size){
        Pageable pageable = PageRequest.of(page,size,
                Sort.by(Sort.Direction.DESC,"createDate"));
        Page<ComentEntity> pageList = repository.findAllByProfileId(pId, pageable);

        List<ComentDto> dtoList = new LinkedList<>();
        for (ComentEntity entity : pageList.getContent()) {
            dtoList.add(toDto(entity));

        }
        return new PageImpl<>(dtoList,pageable,pageList.getTotalElements());
    }
    private ComentDto toDto(ComentEntity comentEntity) {
        ComentDto dto = new ComentDto();
        dto.setId(comentEntity.getId());
        dto.setTitle(comentEntity.getTitle());
        dto.setProfileId(comentEntity.getProfileId());
        dto.setArticle_id(comentEntity.getArticle_id());
        dto.setCreateDate(comentEntity.getCreateDate());
        dto.setUpdateDate(comentEntity.getUpdateDate());
        return dto;
    }

}
