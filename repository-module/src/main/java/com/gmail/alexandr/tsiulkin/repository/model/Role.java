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
@ToString(exclude = "users")
@EqualsAndHashCode(exclude = "users")
@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(cascade = CascadeType.MERGE, orphanRemoval = true)
    @JoinColumn(name = "role_id")
    private Set<User> users = new HashSet<>();
    @Column(name = "role_name")
    private String roleName;
}
