package com.company.repository;

import com.company.entity.ArticleEntity;
import com.company.enums.ArticleStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface ArticleRepository extends JpaRepository<ArticleEntity,Integer> {

    @Modifying
    @Transactional
    @Query(value = "update ArticleEntity set viewCount= :view where id=:id")
    void updateViewCount(@Param("view")long l, @Param("id")Integer id);

    Page<ArticleEntity> findAllByProfileId(Integer pId, Pageable pageable);


    Page<ArticleEntity> findAllByCategoryEntityId(Integer pId, Pageable pageable);

    Page<ArticleEntity> findAllByRegionId(Integer pId, Pageable pageable);
    Page<ArticleEntity> findAllByArticleTypeId(Integer pId, Pageable pageable);

    List<ArticleEntity> findTop4By(Sort createDate);

    List<ArticleEntity> findTop4ByRegionId(Integer id,Sort createDate);

    List<ArticleEntity> findTop4ByCategoryEntityId(Integer id,Sort createDate);

    @Modifying
    @Transactional
    @Query(value = "update ArticleEntity set status= :status where id=:id")
    void updateStatus(@Param("status") ArticleStatus status, @Param("id")Integer id);



}
