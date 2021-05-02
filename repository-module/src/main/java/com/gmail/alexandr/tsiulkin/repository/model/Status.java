package com.gmail.alexandr.tsiulkin.repository.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString(exclude = "reviews")
@EqualsAndHashCode(exclude = "reviews")
@Entity
@Table(name = "status_review")
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(cascade = CascadeType.MERGE, orphanRemoval = true)
    @JoinColumn(name = "status_id")
    private Set<Review> reviews = new HashSet<>();
    @Column(name = "status")
    private String status;
}
