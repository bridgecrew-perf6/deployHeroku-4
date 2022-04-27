package com.company.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Table(name = "profile_image")
@Getter
@Setter
public class ProfileImage extends BasicEntity {

    @Column
    private String imageUrl;
    @Column
    private String attachId;

    @Column(name = "profile_id",nullable = false,unique = true)
    private Integer profileId;
    @OneToOne
    @JoinColumn(name = "profile_id",updatable = false,insertable = false)
    private ProfileEntity profile;
}
