package com.company.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "region")
@Setter
@Getter
public class RegionEntity extends BasicEntity{

    @Column(unique = true,nullable = false)
    private String key;
    @Column(name = "name_uz",unique = true,nullable = false)
    private String nameUz;
    @Column(name = "name_ru",unique = true,nullable = false)
    private String nameRu;
    @Column(name = "name_en",unique = true,nullable = false)
    private String nameEn;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private ProfileEntity profile;

}
