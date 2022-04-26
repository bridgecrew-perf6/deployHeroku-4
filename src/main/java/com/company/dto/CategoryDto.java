package com.company.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Setter
@Getter
public class CategoryDto {
    private Integer id;
    private String nameUz;
    private String nameRu;
    private String nameEn;
    private String name;
    private String key;
    private LocalDateTime createDate;
}
