package com.company.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ManyToAny;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "article_tags")
@Getter
@Setter
public class ArticleTagsEntity extends BasicEntity{

    @Column(name = "tag_id",nullable = false)
    private Integer tagId;

    @Column(name = "article_id",nullable = false)
    private Integer articleId;

    @ManyToOne
    @JoinColumn(name = "tag_id",insertable = false,updatable = false)
    private TagsEntity tags;
    @ManyToOne
    @JoinColumn(name = "article_id",insertable = false,updatable = false)
    private ArticleEntity article;

}
