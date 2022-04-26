package com.company.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
public class TagsEntity extends BasicEntity{

    @Column(unique = true)
    private String nameUz;
    @Column(unique = true)
    private String nameEn;
    @Column(unique = true)
    private String nameRu;
    @Column(unique = true)
    private String key;
}
