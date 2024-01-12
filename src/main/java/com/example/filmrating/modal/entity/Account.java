package com.example.filmrating.modal.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "`Account`")
public class Account {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "username", length = 50, nullable = false, unique = true)
    private String username;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "full_name", length = 50)
    private String fullName;

    @Column(name = "email", length = 50, nullable = false)
    private String email;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private AccountStatus status;
}
