package com.company.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
@Getter
@Setter
public class AttachEntity {
    @Id
    private String id;              //uuid
    @Column
    private Long size;
    @Column
    private String path;
    @Column
    private String extencion;
    @Column
    private String orgName;
    @Column
    private LocalDateTime createDate = LocalDateTime.now();
}
