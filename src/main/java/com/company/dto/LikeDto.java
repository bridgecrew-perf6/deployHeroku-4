package com.company.dto;

import com.company.enums.LikeStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class LikeDto {
    private Integer id;
    private LikeStatus status;
    private Integer pId;
    private Integer articleId;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
}
