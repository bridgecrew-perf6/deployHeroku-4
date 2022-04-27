package com.company.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Getter
@Setter
public class ArticleTagsDto {
    private Integer id;
    private Integer tagId;
    private Integer articleId;
    private LocalDateTime createDate;
}
