package com.gmail.alexandr.tsiulkin.repository.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "item_details")
public class ItemDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "content")
    private String shortContent;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", referencedColumnName = "id")
    private Item item;

}
