package com.company.repository;

import com.company.entity.ProfileImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileImageRepository extends JpaRepository<ProfileImage,Integer> {

    Optional<ProfileImage> findByProfileId(Integer id);

    Boolean deleteByProfileId(Integer id);


}
