package com.company.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "category")
@Setter
@Getter
public class CategoryEntity extends BasicEntity{

    @Column
    private String nameUz;
    @Column
    private String nameRu;
    @Column
    private String nameEn;
    @Column
    private String name;
    @Column(unique = true,nullable = false)
    private String key;

}
