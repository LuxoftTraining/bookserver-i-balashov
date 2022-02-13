package ru.ibalashov.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "word", indexes = @Index(columnList = "word"))
public class Word {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "word")
    private String word;
}
