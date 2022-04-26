package com.company.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleTypeDto {
    private Integer id;
    private String  nameUz;
    private String  nameRu;
    private String  nameEn;
    private String  name;
    private String  key;
    private LocalDateTime createDate;
}
