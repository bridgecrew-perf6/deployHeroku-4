package com.company.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AttachDto {
    private String id;
    private String orgName;
    private String url;
    private String path;
    private LocalDateTime createDate;
}
