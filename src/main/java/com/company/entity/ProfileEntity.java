package com.company.entity;

import com.company.enums.ProfileRole;
import com.company.enums.ProfileStatus;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "profile")
@Getter
@Setter
public class ProfileEntity extends BasicEntity {

    @Column
    private String name;
    @Column
    private String surname;
    @Column(unique = true,nullable = false)
    private String email;
    @Column
    private String password;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ProfileStatus status;

    @Column(name = "activate_date")
    private LocalDateTime activateDate;
    @Column
    @Enumerated(EnumType.STRING)
    private ProfileRole role;

    @OneToOne
    @JoinColumn(name = "attach_id",nullable = true)
    private AttachEntity attach;

}
