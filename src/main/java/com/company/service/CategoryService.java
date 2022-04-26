package com.company.service;

import com.company.dto.CategoryDto;
import com.company.entity.CategoryEntity;
import com.company.entity.ProfileEntity;
import com.company.enums.LangEnum;
import com.company.enums.ProfileRole;
import com.company.exception.AppForbiddenexception;
import com.company.exception.ItemNotFoundException;
import com.company.repository.CategoryRepository;
import com.company.validation.CategoryValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProfileService service;

    public CategoryDto create(CategoryDto dto, Integer pId) {
        ProfileEntity entityId = service.get(pId);

        CategoryValidation.isValid(dto);
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setNameEn(dto.getNameEn());
        categoryEntity.setNameRu(dto.getNameRu());
        categoryEntity.setNameUz(dto.getNameUz());

        categoryRepository.save(categoryEntity);
        dto.setId(categoryEntity.getId());
        return dto;
    }

    public String delete(Integer id, Integer pId) {

        ProfileEntity entityId = service.get(pId);

        Optional<CategoryEntity> byId = categoryRepository.findById(id);

        if (byId.isEmpty()) {
            throw new ItemNotFoundException("bunday category yoq");
        }
        categoryRepository.deleteById(id);
        return "Success";
    }

    public CategoryDto update(Integer id, CategoryDto dto, Integer pId) {
        ProfileEntity entityId = service.get(pId);

        CategoryValidation.isValid(dto);
        Optional<CategoryEntity> byId = categoryRepository.findById(id);
        if (byId.isEmpty()) {
            throw new ItemNotFoundException("bunday category yo'q");
        }
        CategoryEntity categoryEntity = byId.get();
        categoryEntity.setNameUz(dto.getNameUz());
        categoryEntity.setNameEn(dto.getNameEn());
        categoryEntity.setNameRu(dto.getNameRu());
        categoryRepository.save(categoryEntity);

        return toDO(categoryEntity);

    }

    public List<CategoryDto> getList(Integer pId) {
        ProfileEntity entityId = service.get(pId);

        return categoryRepository.findAll().stream().map(this::toDO).toList();
    }

    public List<CategoryDto> getList(LangEnum lang) {
        List<CategoryDto> list = new LinkedList<>();
        categoryRepository.findAll().stream().toList().forEach(entity -> {
            CategoryDto dto = new CategoryDto();
            dto.setKey(entity.getKey());
            switch (lang) {
                case uz -> dto.setName(entity.getNameUz());
                case en -> dto.setNameEn(entity.getNameEn());
                case ru -> dto.setNameRu(entity.getNameRu());
            }
            list.add(dto);

        });
        return list;
    }

    public CategoryDto toDO(CategoryEntity entity) {
        CategoryDto dto = new CategoryDto();
        dto.setId(entity.getId());
        dto.setNameEn(entity.getNameEn());
        dto.setNameRu(entity.getNameRu());
        dto.setNameUz(entity.getNameUz());
        dto.setCreateDate(entity.getCreateDate());
        return dto;
    }

    public CategoryDto getById(Integer id, Integer pId) {
        ProfileEntity entityId = service.get(pId);

        return toDO(categoryRepository.findById(id).orElseThrow(() ->
                new ItemNotFoundException("Bunday category yoq")));
    }
}
