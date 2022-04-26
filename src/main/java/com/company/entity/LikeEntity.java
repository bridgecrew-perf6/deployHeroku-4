package com.company.entity;


import com.company.enums.LikeStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class LikeEntity extends BasicEntity {

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private LikeStatus status;

    @Column(name = "profile_id",nullable = false)
    private Integer profileId;
    @ManyToOne
    @JoinColumn(name = "profile_id",updatable = false,insertable = false)
    private ProfileEntity profile;

    @Column(name = "article_id",nullable = false)
    private Integer articleId;
    @ManyToOne
    @JoinColumn(name = "article_id",insertable = false,updatable = false)
    private ArticleEntity article;



}
