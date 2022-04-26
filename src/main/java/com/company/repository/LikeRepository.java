package com.company.repository;

import com.company.entity.LikeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<LikeEntity,Integer> {

    Page<LikeEntity> findAllByArticleId(Integer articleId, Pageable pageable);

    Page<LikeEntity> findAllByProfileId(Integer pId, Pageable pageable);
}
