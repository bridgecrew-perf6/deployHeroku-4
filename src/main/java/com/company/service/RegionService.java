package com.company.service;

import com.company.dto.RegionDto;
import com.company.entity.ProfileEntity;
import com.company.entity.RegionEntity;
import com.company.enums.LangEnum;
import com.company.enums.ProfileRole;
import com.company.exception.AppForbiddenexception;
import com.company.exception.ItemNotFoundException;
import com.company.repository.RegionRepository;
import com.company.validation.RegionValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class RegionService {
    @Autowired
    private RegionRepository regionRepository;
    @Autowired
    private ProfileService service;

    public RegionDto create(RegionDto dto, Integer pId) {
        ProfileEntity entityId = service.get(pId);

        RegionValidation.isValid(dto);
        RegionEntity entity = new RegionEntity();
        entity.setNameEn(dto.getNameEn());
        entity.setNameRu(dto.getNameRu());
        entity.setNameUz(dto.getNameUz());
        entity.setProfile(entityId);
        entity.setKey(dto.getKey());
        regionRepository.save(entity);
        dto.setId(entity.getId());
        dto.setCreateDate(entity.getCreateDate());
        return dto;
    }
    public String delete(Integer id, Integer pId) {
        ProfileEntity entityId = service.get(pId);
        Optional<RegionEntity> byId = regionRepository.findById(id);
        if (byId.isEmpty()) {
            throw new ItemNotFoundException("bunday category yoq");
        }
        regionRepository.deleteById(id);
        return "Success";
    }

    public RegionDto update(Integer id, RegionDto dto,Integer pid) {
        ProfileEntity entityId = service.get(pid);

        RegionValidation.isValid(dto);
        Optional<RegionEntity> byId = regionRepository.findById(id);
        if (byId.isEmpty()) {
            throw new ItemNotFoundException("bunday category yo'q");
        }
        RegionEntity categoryEntity = byId.get();
        categoryEntity.setNameUz(dto.getNameUz());
        categoryEntity.setNameEn(dto.getNameEn());
        categoryEntity.setNameRu(dto.getNameRu());
        categoryEntity.setKey(dto.getKey());
        categoryEntity.setProfile(entityId);
        regionRepository.save(categoryEntity);
        return toDO(categoryEntity);
    }
    public List<RegionDto> getList(Integer pid) {
        ProfileEntity entityId = service.get(pid);
        if(!entityId.getRole().equals(ProfileRole.ADMIN)){
            throw new AppForbiddenexception("profile role admin emas");
        }
        return regionRepository.findAll().stream().map(this::toDO).toList();
    }
    public List<RegionDto> getList(LangEnum lang) {
        List<RegionDto> list = new LinkedList<>();
         regionRepository.findAll().stream().toList().forEach(regionEntity -> {
             RegionDto dto = new RegionDto();
             dto.setKey(regionEntity.getKey());
             switch (lang){
                 case uz -> dto.setName(regionEntity.getNameUz());
                 case en -> dto.setNameEn(regionEntity.getNameEn());
                 case ru -> dto.setNameRu(regionEntity.getNameRu());
             }
             list.add(dto);

         });
         return list;
    }
    public RegionDto toDO(RegionEntity entity) {
        RegionDto dto = new RegionDto();
        dto.setId(entity.getId());
        dto.setNameEn(entity.getNameEn());
        dto.setNameRu(entity.getNameRu());
        dto.setNameUz(entity.getNameUz());
        dto.setKey(entity.getKey());
        dto.setCreateDate(entity.getCreateDate());
        return dto;
    }
}
