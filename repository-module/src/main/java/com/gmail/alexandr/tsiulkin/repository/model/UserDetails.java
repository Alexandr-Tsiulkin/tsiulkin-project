package com.gmail.alexandr.tsiulkin.repository.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user_details")
public class UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String address;
    @Column
    private String telephone;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
