package com.company.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
@Getter
@Setter
public class ArticleEntity extends BasicEntity{
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String description;
    @Column
    private String content;
    @Column
    private Boolean visable= true;
    @Column
    private LocalDateTime publicDate;
    @Column(name = "view",nullable = false)
    private Long viewCount;

    @Column(name = "type_id",nullable = false)
    private Integer typeId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id",insertable = false,updatable = false)
    private ArticleTypeEntity articleType;

    @Column(name = "category_id",nullable = false)
    private Integer categoryId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id",insertable = false,updatable = false)
    private CategoryEntity categoryEntity;

    @Column(name = "attach_id",nullable = false)
    private String attachId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attach_id",insertable = false,updatable = false)
    private AttachEntity attach;


    @Column(name = "region_id",nullable = false)
    private Integer regionId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id",insertable = false,updatable = false)
    private RegionEntity region;

    @Column(name = "profile_id",nullable = false)
    private Integer profileId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id",insertable = false,updatable = false)
    private ProfileEntity profile;


}
