package com.company.repository;

import com.company.entity.LikeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<LikeEntity,Integer> {

    Page<LikeEntity> findAllByArticleId(Integer articleId, Pageable pageable);

    Page<LikeEntity> findAllByProfileId(Integer pId, Pageable pageable);

    Optional<LikeEntity> findAllByProfileIdAndArticleId(Integer profileId, Integer article);
}
