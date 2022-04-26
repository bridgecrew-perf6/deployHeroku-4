package com.company.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "email_history")
@Getter
@Setter

public class EmailEntity extends BasicEntity {

    @Column(name = "to_email")
    private String toEmail;
    @Column
    private String title;
    @Column
    private String content;
    @Column(name = "activate_date")
    private LocalDateTime activateDate;

}
