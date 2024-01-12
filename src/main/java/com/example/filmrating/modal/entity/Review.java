package com.example.filmrating.modal.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "`Review`")
public class Review {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "post_date")
    private Date postDate;

    @Column(name = "update_date")
    private Date updateDate;

    @Column(name = "rating", nullable = false)
    private float rating;

    @ManyToOne()
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne()
    @JoinColumn(name = "film_id")
    private Film film;

    @PrePersist
    public void onPrePersist(){
        this.postDate = new Date();
    }

    @PreUpdate
    public void onPreUpdate() {
        this.updateDate = new Date();
    }

}
