package com.company.repository;

import com.company.entity.ComentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<ComentEntity,Integer> {
    Page<ComentEntity> findAllByArticleId(Integer articleId, Pageable pageable);

    Page<ComentEntity> findAllByProfileId(Integer pId, Pageable pageable);
}
