package com.company.dto;

import com.company.enums.ArticleStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ArticleDto {
    private Integer id;
    private String title;
    private String description;
    private String content;
    private Integer regionId;
    private Integer typeId;
    private Integer viewCount;
    private ArticleStatus status;
    private Integer categoryId;
    private LocalDateTime createDate;
}
