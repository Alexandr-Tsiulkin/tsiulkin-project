package com.gmail.alexandr.tsiulkin.repository.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@Entity
@Table(name = "item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title")
    private String title;
    @Type(type="uuid-char")
    @Column(name = "unique_number")
    private UUID uuid;
    @Column(name = "price")
    private BigDecimal price;
    @OneToOne(fetch = FetchType.LAZY,
            mappedBy = "item",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private ItemDetails itemDetails;


}
