package com.company.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "article_type")
@Setter
@Getter
public class ArticleTypeEntity extends BasicEntity{


    @Column(name = "name_uz",nullable = false)
    private String  nameUz;
    @Column(nullable = false,name = "name_ru")
    private String  nameRu;
    @Column(nullable = false,name = "name_en")
    private String  nameEn;
    @Column(unique = true)
    private String  key;

}
