package com.company.service;

import com.company.dto.*;
import com.company.entity.ProfileEntity;
import com.company.enums.ProfileRole;
import com.company.enums.ProfileStatus;
import com.company.exception.EmailAlreadyException;
import com.company.exception.ItemNotFoundException;
import com.company.exception.PasswordOrEmailWrongEmail;
import com.company.repository.EmailRepository;
import com.company.repository.ProfileRepository;
import com.company.util.JwtUtil;
import com.company.validation.RegValidation;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private EmailService emailService;

    @Autowired
    private EmailRepository emailRepository;
    @Autowired
    private ProfileImageService profileImageService;


    public ProfileDto login(AuthDto dto) {
        Optional<ProfileEntity> byEmail = profileRepository.findByEmail(dto.getEmail());
        if (byEmail.isEmpty()) {
            throw new PasswordOrEmailWrongEmail("Bunday profile user yoq");
        }

        String pswd = DigestUtils.md5Hex(dto.getPassword());

        if (!byEmail.get().getPassword().equals(pswd)
                || !byEmail.get().getStatus().equals(ProfileStatus.ACTIVE)) {
            throw new PasswordOrEmailWrongEmail("parol xato, yoki activ bolmagan ");
        }
        ProfileImageDto imageDto = profileImageService.get(byEmail.get().getId());
        ProfileDto profileDto = toDO(byEmail.get());
        profileDto.setJwt(JwtUtil.encode(byEmail.get().getId(), byEmail.get().getRole()));
        if(imageDto != null){
            profileDto.setProfileImage(imageDto.getImageUrl());
        }
        return profileDto;
    }

    public AuthDto registration(RegDto dto) {
        RegValidation.isValid(dto);
        Optional<ProfileEntity> byEmail = profileRepository.findByEmail(dto.getEmail());
        if (byEmail.isPresent()) {
            throw new EmailAlreadyException("bunday email bor!");
        }
        ProfileEntity profile = new ProfileEntity();
        profile.setRole(ProfileRole.USER);
        profile.setEmail(dto.getEmail());
        profile.setName(dto.getName());
        profile.setSurname(dto.getSurname());
        String pswd = DigestUtils.md5Hex(dto.getPassword());
        profile.setPassword(pswd);
        profile.setStatus(ProfileStatus.NO_ACTIVE);
        profileRepository.save(profile);

        Thread thread = new Thread() {
            @Override
            public void run() {
                String jwt = JwtUtil.createJwt(profile.getId(), profile.getRole());
                String content = "Activate your acaunt\n " +
                        "http://localhost:8080/auth/verification/" + jwt;
                emailService.send(dto.getEmail(), "Activate your  registration", content);
            }
        };
        thread.start();
        AuthDto authDto = new AuthDto();
        authDto.setEmail(profile.getEmail());
        authDto.setPassword(dto.getPassword());
        return authDto;
    }

    public ProfileDto toDO(ProfileEntity entity) {
        ProfileDto profileDto = new ProfileDto();
        profileDto.setName(entity.getName());
        profileDto.setSurname(entity.getSurname());
        profileDto.setEmail(entity.getEmail());
        profileDto.setPassword(entity.getPassword());
        profileDto.setCreateDate(entity.getCreateDate());
        profileDto.setRole(entity.getRole());
        return profileDto;
    }

    public String verification(String jwt) {
        Optional<ProfileEntity> byId =
                profileRepository.findById(JwtUtil.deCodeVerification(jwt));
        if (byId.isEmpty()) {
            throw new ItemNotFoundException("Not found");
        }
        ProfileEntity entity = byId.get();
        entity.setActivateDate(LocalDateTime.now());
        entity.setStatus(ProfileStatus.ACTIVE);
        profileRepository.save(entity);
        return "Activated";
    }


}
