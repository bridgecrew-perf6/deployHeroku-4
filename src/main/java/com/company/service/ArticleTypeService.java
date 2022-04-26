package com.company.service;

import com.company.dto.ArticleTypeDto;
import com.company.entity.ArticleTypeEntity;
import com.company.entity.ProfileEntity;
import com.company.enums.LangEnum;
import com.company.enums.ProfileRole;
import com.company.exception.AppForbiddenexception;
import com.company.exception.ItemNotFoundException;
import com.company.repository.ArticleTypeRepository;
import com.company.validation.ArticleTypeValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleTypeService {
    @Autowired
    private ArticleTypeRepository repository;
    @Autowired
    private ProfileService service;

    public ArticleTypeDto create(ArticleTypeDto dto,Integer pId){
        ProfileEntity entityId = service.get(pId);

        ArticleTypeValidation.isValid(dto);
        ArticleTypeEntity entity = new ArticleTypeEntity();
        entity.setKey(dto.getKey());
        entity.setNameEn(dto.getNameEn());
        entity.setNameRu(dto.getNameRu());
        entity.setNameUz(dto.getNameUz());
        repository.save(entity);
        dto.setId(entity.getId());
        dto.setCreateDate(entity.getCreateDate());
        return dto;
    }

    public String delete(Integer id, Integer pId){
         ProfileEntity entityId = service.get(pId);

        Optional<ArticleTypeEntity> byId = repository.findById(id);
        if(byId.isEmpty()){
            throw new ItemNotFoundException("Bunday article yoq");
        }
        repository.deleteById(id);
        return "Success";
    }

    public ArticleTypeDto update(Integer id, ArticleTypeDto dto, Integer pId){
        ProfileEntity entityId = service.get(pId);
        if(!entityId.getRole().equals(ProfileRole.ADMIN)){
            throw new AppForbiddenexception("profile role admin emas");
        }
        Optional<ArticleTypeEntity> byId = repository.findById(id);
        if(byId.isEmpty()){
            throw new ItemNotFoundException("Bunday article yoq");
        }
        ArticleTypeValidation.isValid(dto);
        ArticleTypeEntity entity = byId.get();
        entity.setKey(dto.getKey());
        entity.setNameEn(dto.getNameEn());
        entity.setNameRu(dto.getNameRu());
        entity.setNameUz(dto.getNameUz());
        repository.save(entity);
        dto.setId(entity.getId());
        dto.setCreateDate(entity.getCreateDate());
        return dto;
    }

    public List<ArticleTypeDto> list(Integer pId){
        ProfileEntity entityId = service.get(pId);
        if(!entityId.getRole().equals(ProfileRole.ADMIN)){
            throw new AppForbiddenexception("profile role admin emas");
        }
       List<ArticleTypeDto> list = new LinkedList<>();
        repository.findAll().forEach(articleTypeEntity -> {
            list.add(toDo(articleTypeEntity));
        });
        return list;
    }

    public List<ArticleTypeDto> list(LangEnum lang){
        List<ArticleTypeDto> list = new LinkedList<>();
        repository.findAll().forEach(articleTypeEntity -> {
            ArticleTypeDto dto = new ArticleTypeDto();
            dto.setId(articleTypeEntity.getId());
            dto.setKey(articleTypeEntity.getKey());
            switch (lang){
                case uz -> dto.setName(articleTypeEntity.getNameUz());
                case ru -> dto.setName(articleTypeEntity.getNameRu());
                case en -> dto.setName(articleTypeEntity.getNameEn());
            }
            list.add(dto);
        });
        return list;
    }

    public ArticleTypeDto getById(Integer id){
        Optional<ArticleTypeEntity> byId = repository.findById(id);
        if(byId.isEmpty()){
            throw new ItemNotFoundException("Bunday article yoq");
        }
        return toDo(byId.get());
    }
    private ArticleTypeDto toDo(ArticleTypeEntity entity) {
        ArticleTypeDto dto = new ArticleTypeDto();
        dto.setId(entity.getId());
        dto.setNameEn(entity.getNameEn());
        dto.setNameRu(entity.getNameRu());
        dto.setNameUz(entity.getNameUz());
        dto.setKey(entity.getKey());
        dto.setCreateDate(entity.getCreateDate());
        return dto;

    }


}
