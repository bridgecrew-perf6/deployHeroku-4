package com.company.service;

import com.company.dto.AttachDto;
import com.company.dto.ProfileImageDto;
import com.company.entity.AttachEntity;
import com.company.entity.ProfileImage;
import com.company.exception.ItemNotFoundException;
import com.company.repository.ProfileImageRepository;
import com.company.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
public class ProfileImageService {
    @Autowired
    private ProfileImageRepository repository;
    @Autowired
    private AttachService attachService;
    @Autowired
    private ProfileRepository profileRepository;
    @Value("${server.domen.name}")
    private String urlDowload;
    @Value("${attach.upload.folder}")
    private String attachfolder;

    public ProfileImageDto upload(Integer id, MultipartFile file) {
        AttachDto dto= attachService.upload(file);
        AttachEntity entity = attachService.getById(dto.getId());
        String imageUrl =urlDowload+"/attach/open1/"+ dto.getId();

        ProfileImage profileImage = new ProfileImage();
        profileImage.setProfileId(id);
        profileImage.setAttachId(entity.getId());
        profileImage.setImageUrl(imageUrl);
        repository.save(profileImage);
        profileRepository.updateImage(entity.getId(),id);
        return toDo(profileImage);
    }

    public ProfileImageDto get(Integer id){
        Optional<ProfileImage> optional = repository.findByProfileId(id);
        if(optional.isEmpty()){
            return null;
        }
        return toDo(optional.get());
    }
    public Boolean delete(Integer id){
        return repository.deleteByProfileId(id);
    }
    private ProfileImageDto toDo(ProfileImage profileImage) {
        ProfileImageDto dto = new ProfileImageDto();
        dto.setId(profileImage.getId());
        dto.setProfileId(profileImage.getProfileId());
        dto.setImageUrl(profileImage.getImageUrl());
        return dto;
    }
}
