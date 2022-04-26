package com.company.service;

import com.company.dto.ProfileDto;
import com.company.entity.ProfileEntity;
import com.company.enums.ProfileRole;
import com.company.enums.ProfileStatus;
import com.company.exception.AppForbiddenexception;
import com.company.exception.EmailAlreadyException;
import com.company.exception.ItemNotFoundException;
import com.company.repository.ProfileRepository;
import com.company.validation.ProfileValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;


    public ProfileDto create(ProfileDto dto, Integer pId){
        ProfileEntity entity1 = get(pId);

        ProfileValidation.isValid(dto);
        Optional<ProfileEntity> byEmail = profileRepository.findByEmail(dto.getEmail());
        if(byEmail.isPresent()){
            throw new EmailAlreadyException("bunday email bor!");
        }
        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());
        entity.setRole(dto.getRole());
        entity.setStatus(ProfileStatus.ACTIVE);
        profileRepository.save(entity);
        dto.setCreateDate(entity.getCreateDate());
        dto.setId(entity.getId());
        return dto;


    }

    public ProfileDto update(Integer id, ProfileDto dto,Integer pId){
        ProfileEntity entityId = get(pId);
        ProfileValidation.isValid(dto);
        Optional<ProfileEntity> byEmail = profileRepository.findById(id);
        if(byEmail.isEmpty()){
            throw new EmailAlreadyException("bunday profile  yoq!");
        }

        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());
        entity.setRole(dto.getRole());
        profileRepository.save(entity);
        return toDO(entity);
    }

    public String delete(Integer id,Integer pId){
        ProfileEntity entityId = get(pId);
        Optional<ProfileEntity> byId = profileRepository.findById(id);
        if(byId.isEmpty()){
            throw new ItemNotFoundException("bunday profil yoq");
        }
        profileRepository.deleteById(id);
        return "Success";
    }

    public ProfileEntity get(Integer id ){
        return profileRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("Not found!"));
    }
    public List<ProfileDto> getList(Integer pId) {
        ProfileEntity entityId = get(pId);

        List<ProfileDto> list = new LinkedList<>();
        profileRepository.findAll().forEach(entity -> {
             list.add(toDO(entity));
         });
        return list;
    }
    public List<ProfileDto> getList(Pageable pageable,Integer pId){
        ProfileEntity entityId = get(pId);
        Page<ProfileEntity> page = profileRepository.findAll(pageable);
       return  page.stream().map(this::toDO).toList();
    }
    public  ProfileDto toDO(ProfileEntity entity){
        ProfileDto profileDto = new ProfileDto();
        profileDto.setId(entity.getId());
        profileDto.setName(entity.getName());
        profileDto.setSurname(entity.getSurname());
        profileDto.setEmail(entity.getEmail());
        profileDto.setPassword(entity.getPassword());
        profileDto.setCreateDate(entity.getCreateDate());
        profileDto.setRole(entity.getRole());
        return profileDto;
    }

}
