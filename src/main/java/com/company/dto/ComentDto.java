package com.company.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Getter
@Setter
public class ComentDto {

    private Integer id;
    private String title;
    private Integer profileId;
    private Integer article_id;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
}
