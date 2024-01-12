package com.example.filmrating.modal.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "`active_user`")
public class ActiveUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "uuid", unique = true)
    private String uuid;

    @OneToOne
    @JoinColumn(name = "user_id")
    private Account account;

}
