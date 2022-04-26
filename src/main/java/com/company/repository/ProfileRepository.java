package com.company.repository;

import com.company.entity.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;

public interface ProfileRepository extends JpaRepository<ProfileEntity,Integer> {

    Optional<ProfileEntity> findByEmail(String email);

    @Modifying
    @Transactional
    @Query(value = "update ProfileEntity set attach.id = :attachId where id=:id")
    void updateImage(@Param("attachId") String imageId,@Param("id") Integer id);
}
