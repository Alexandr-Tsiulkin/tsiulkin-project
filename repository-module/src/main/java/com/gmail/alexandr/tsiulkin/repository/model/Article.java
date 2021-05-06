package com.gmail.alexandr.tsiulkin.repository.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "article")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "date")
    private LocalDateTime localDateTime;
    @Column
    private String title;
    @Column(name = "content")
    private String fullContent;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "seller_id")
    private User user;
}
