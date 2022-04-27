package com.company.repository;

import com.company.entity.ArticleTagsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ArticleTagsRepository extends JpaRepository<ArticleTagsEntity,Integer> {
    void deleteByArticleIdAndTagId(Integer articleId,Integer tagId);

    Optional<ArticleTagsEntity> findByArticleIdAndTagId(Integer articleId,Integer tagId);

    List<ArticleTagsEntity> findAllByArticleId(Integer id);
}
