package com.company.dto;

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
    private Long viewCount;
    private LocalDateTime createDate;
}
