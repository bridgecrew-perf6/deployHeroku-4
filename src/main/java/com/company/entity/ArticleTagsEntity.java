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
public class ArticleTagsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "tag_id")
    private TagsEntity tags;
    @ManyToOne
    @JoinColumn(name = "article_id")
    private ArticleEntity article;

}
